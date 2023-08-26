package org.mifos.chatbot.server.config.openFeign.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetClientInfoResponse {
    private int id;
    private String accountNo;
    private boolean active;
    private List<Integer> activationDate;
    private String firstname;
    private String lastname;
    private String displayName;
    private boolean isStaff;
    private int officeId;
    private String officeName;
    private String savingsProductName;
}
