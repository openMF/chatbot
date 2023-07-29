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
public class Slots {
    Boolean auto_fill;
    String initial_value;
    String type;
    List<String> values;
}
