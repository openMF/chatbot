package org.mifos.chatbot.server.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Intent {
    Double confidence;
    String name;
}