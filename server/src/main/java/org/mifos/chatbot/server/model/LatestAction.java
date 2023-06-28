package org.mifos.chatbot.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LatestAction {
    String actionName;
    String actionText;
}
