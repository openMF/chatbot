package org.mifos.chatbot.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mifos.chatbot.server.model.Intent;
import org.mifos.chatbot.server.model.Tracker;

import java.io.IOException;

public interface ChatService {

    void processUserUtterance(String botResponse, String conversationId) throws IOException;
    Tracker retriveConversationTracker(String conversationId) throws IOException;

    Intent findIntent(Tracker tracker);

    String classifyIntent(String botResponse, Intent intent, Tracker tracker);

    String getResponse(String message) throws IOException;
}



