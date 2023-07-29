package org.mifos.chatbot.server.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mifos.chatbot.server.model.Tracker;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomActionServerRequest {
    String next_action;
    String sender_id;
    Tracker tracker;
    Domain domain;


}
