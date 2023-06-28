package org.mifos.chatbot.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.mifos.chatbot.server.api.DefaultApi;
import org.mifos.chatbot.server.config.DisableCertificateValidation;
import org.mifos.chatbot.server.config.openFeign.FineractService;
import org.mifos.chatbot.server.model.Tracker;
import org.mifos.chatbot.server.request.PostAuthenticationRequest;
import org.mifos.chatbot.server.service.Helper;
import org.mifos.chatbot.server.service.Impl.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "/")
public class RasaController {

    @Autowired
    private FineractService fineractService;

    @Value("${RASA_SERVER}")
    private String RASA_SERVER;

    @Value("${WEBHOOK}")
    private String WEBHOOK;

    ChatServiceImpl chatService = new ChatServiceImpl();

    Helper helper = new Helper();

    // TODO check if it should be get mapping
    @PostMapping("/getUserUtterance")
    public String getUserUtterance(@RequestParam String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, helper.createJSONRequest(message));
        String response = chatService.getResponse(message);
        //TODO how to fetch conversationId?
        chatService.processUserUtterance(response, "default");
        return null;
    }
}
