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
public class Period {
    private List<Integer> dueDate;
    private double principalDisbursed;
    private double principalLoanBalanceOutstanding;
    private double feeChargesDue;
    private double feeChargesPaid;
    private double totalOriginalDueForPeriod;
    private double totalDueForPeriod;
    private double totalPaidForPeriod;
    private double totalActualCostOfLoanForPeriod;
    private int totalCredits;
    private int period;
    private List<Integer> fromDate;
    private boolean complete;
    private int daysInPeriod;
    private double principalOriginalDue;
    private double principalDue;
    private int principalPaid;
    private int principalWrittenOff;
    private double principalOutstanding;
    private int interestOriginalDue;
    private int interestDue;
    private int interestPaid;
    private int interestWaived;
    private int interestWrittenOff;
    private int interestOutstanding;
    private double feeChargesWaived;
    private int feeChargesWrittenOff;
    private double feeChargesOutstanding;
    private int penaltyChargesDue;
    private int penaltyChargesPaid;
    private int penaltyChargesWaived;
    private int penaltyChargesWrittenOff;
    private int penaltyChargesOutstanding;
    private int totalPaidInAdvanceForPeriod;
    private int totalPaidLateForPeriod;
    private double totalWaivedForPeriod;
    private int totalWrittenOffForPeriod;
    private double totalOutstandingForPeriod;
    private double totalInstallmentAmountForPeriod;

}
