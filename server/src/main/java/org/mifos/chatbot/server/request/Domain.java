package org.mifos.chatbot.server.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Domain {
    Config config;
    List<String> entities;
    Slots slots;
    List<String> responses;
    List<String> actions;

}
