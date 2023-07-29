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
public class RepaymentSchedule {
    //private Currency currency;
    private int loanTermInDays;
    private double totalPrincipalDisbursed;
    private double totalPrincipalExpected;
    private double totalPrincipalPaid;
    private double totalInterestCharged;
    private double totalFeeChargesCharged;
    private double totalPenaltyChargesCharged;
    private double totalWaived;
    private double totalWrittenOff;
    private double totalRepaymentExpected;
    private double totalRepayment;
    private double totalPaidInAdvance;
    private double totalPaidLate;
    private double totalOutstanding;
    private double totalCredits;
    private List<Period> periods;
}
