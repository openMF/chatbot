package org.mifos.chatbot.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LatestMessage {
    Entity entity;
    Intent intent;
    List<Intent> intentRanking;
    String text;
}
