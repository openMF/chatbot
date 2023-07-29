package org.mifos.chatbot.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collation = "client")
public class Client {
    @Id
    private Integer id;
    private String adminUsername;
    private String adminPassword;
    private String clientUsername;
}
