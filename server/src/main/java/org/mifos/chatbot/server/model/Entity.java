package org.mifos.chatbot.server.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Entity {
    int start;
    int end;
    String value;
    String entity;
    Long confidence;
}
