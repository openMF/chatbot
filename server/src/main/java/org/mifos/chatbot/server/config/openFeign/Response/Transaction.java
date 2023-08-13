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
public class Transaction {
    private Double amount;
    private Double netDisbursalAmount;
    private Double principalPortion;
    private Double interestPortion;
    private Integer feeChargesPortion;
    private Integer penaltyChargesPortion;
    private Integer overpaymentPortion;
    private Integer unrecognizedIncomePortion;
    private Double outstandingLoanBalance;
    public List<Integer> date;
}
