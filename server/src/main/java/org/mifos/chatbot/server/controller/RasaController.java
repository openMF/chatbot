package org.mifos.chatbot.server.controller;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import org.mifos.chatbot.server.service.Helper;
import org.mifos.chatbot.server.service.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping(path = "/")
public class RasaController {
    @Value("${RASA_SERVER}")
    private String RASA_SERVER;

    @Value("${WEBHOOK}")
    private String WEBHOOK;

    @Autowired
    ChatServiceImpl chatService;

    Helper helper = new Helper();

    @PostMapping("/getUserUtterance")
    public String getUserUtterance(@RequestParam String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, helper.createJSONRequest(message));
        String response = chatService.getResponse(message);
        //TODO how to fetch conversationId?
        return chatService.processUserUtterance(response, "default");
    }
}
