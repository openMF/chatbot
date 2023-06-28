package org.mifos.chatbot.server.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
    Refer to
    https://rasa.com/docs/rasa/pages/http-api#operation/getConversationTracker
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Tracker {
    String conversationId;
    List<String> slots;
    LatestMessage latestMessage;
    Long latestEventTime;
    String followupAction;
    Boolean paused;
    // TODO something events
    String latestInputChannel;
    String latestActionName;
    LatestAction latestAction;
    String activeLoopName;

}

