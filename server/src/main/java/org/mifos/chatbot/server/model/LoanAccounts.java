package org.mifos.chatbot.server.model;

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
public class LoanAccounts {
    private List<LoanAccount> loanAccounts;
    private List<Object> groupLoanIndividualMonitoringAccounts;
    private List<Object> guarantorAccounts;
}
