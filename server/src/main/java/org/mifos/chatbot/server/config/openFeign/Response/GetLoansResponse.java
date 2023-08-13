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
public class GetLoansResponse {

    private Integer annualInterestRate;
    private Integer approvedPrincipal;
    private List<DisbursementDetail> disbursementDetails;
    private Timeline timeline;
    private RepaymentSchedule repaymentSchedule;
    public List<Transaction> transactions;
    public Delinquent delinquent;

}
