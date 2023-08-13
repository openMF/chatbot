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
public class Delinquent {
    public double availableDisbursementAmount;
    public int pastDueDays;
    public List<Integer> nextPaymentDueDate;
    public int delinquentDays;
    public List<Integer> delinquentDate;
    public double delinquentAmount;
    public List<Integer> lastPaymentDate;
    public double lastPaymentAmount;
    public List<Integer> lastRepaymentDate;
    public double lastRepaymentAmount;
}
