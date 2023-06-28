package org.mifos.chatbot.server.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.mifos.chatbot.server.model.Intent;
import org.mifos.chatbot.server.model.Tracker;
import org.mifos.chatbot.server.service.Helper;
import org.mifos.chatbot.server.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatServiceImpl implements ChatService {

    private static final String LOGIN_CREDENTIALS = "login_credentials";
    @Value("${RASA_SERVER}")
    private String RASA_SERVER = "http://localhost:5005";

    @Value("${WEBHOOK}")
    private String WEBHOOK = "webhooks/rest/webhook";

    Helper helper = new Helper();
    FineractServiceImpl fineractService = new FineractServiceImpl();

    @Override
    public void processUserUtterance(String botResponse, String conversationId) throws IOException {
        Tracker tracker = retriveConversationTracker(conversationId);
        Intent intent = findIntent(tracker);
        classifyIntent(botResponse, intent, tracker);
    }

    @Override
    public String getResponse(String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, helper.createJSONRequest(message));
        Request request = new Request.Builder()
                .url(RASA_SERVER + "\\" + WEBHOOK)
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() != 200) {
            //TODO handle exception
        }
        return response.message();
    }

    @Override
    public String classifyIntent(String botResponse, Intent intent, Tracker tracker) {
        String intentName = intent.getName();
        if(intentName.equals(LOGIN_CREDENTIALS)) {
            // TODO : call fineract authorization API
            fineractService.authorization(tracker.getLatestMessage().getText());
        }

        return botResponse;
    }

    @Override
    public Tracker retriveConversationTracker(String conversationId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(RASA_SERVER + "/conversations/" + conversationId + "/tracker")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() != 200) {
            //TODO add logging
        }
        JsonObject obj = helper.createJSON(response.body().string());
        Tracker tracker = helper.createTrackerPOJO(obj);
        return tracker;
    }

    @Override
    public Intent findIntent(Tracker tracker) {
        return tracker.getLatestMessage().getIntent();
    }
}
