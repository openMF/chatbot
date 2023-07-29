package org.mifos.chatbot.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Status {
    private int id;
    private String code;
    private String value;
    private Boolean pendingApproval;
    private Boolean waitingForDisbursal;
    private Boolean active;
    private Boolean closedObligationsMet;
    private Boolean closedWrittenOff;
    private Boolean closedRescheduled;
    private Boolean closed;
    private Boolean overpaid;
}
