package org.mifos.chatbot.server.service;

import org.mifos.chatbot.server.config.openFeign.Response.*;
import org.mifos.chatbot.server.model.LoanAccount;
import org.mifos.chatbot.server.model.LoanAccounts;
import org.mifos.chatbot.server.model.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetLoanServiceImpl {

    @Autowired
    private FineractServiceImpl fineractService;

    Helper helper = new Helper();

    public String getLoanStatus(String botResponse, Tracker tracker) {
        LoanAccounts loanAccounts = fineractService.getClientDetails();
        List<LoanAccount> loanAccountList = loanAccounts.getLoanAccounts();
        for(LoanAccount account : loanAccountList) {
            String loanAccountStatus = account.getStatus().getValue();
            return "Your loan status is ".concat(loanAccountStatus);
        }
        return null;
    }

    public Integer getApprovedPrincipalAmount(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        return getLoansResponse.getApprovedPrincipal();
    }

    public Integer getInterestRate(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        return getLoansResponse.getAnnualInterestRate();
    }

    public String getMaturityDate(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        Timeline time = getLoansResponse.getTimeline();
        List<Integer> dateList = time.getExpectedMaturityDate();
        return helper.getDate(dateList);
    }

    public String getPreviousPaymentDate(String botResponse){
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        List<Transaction> transactions = getLoansResponse.getTransactions();
        if(!transactions.isEmpty()) {
            Transaction latest = transactions.get(transactions.size()-1);
            return helper.getDate(latest.getDate());
        }
        return "No payments made";
    }

    public String getPreviousPaymentAmount (String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        List<Transaction> transactions = getLoansResponse.getTransactions();
        if(!transactions.isEmpty()) {
            Transaction latest = transactions.get(transactions.size()-1);
            return latest.getAmount().toString();
        }
        return "No payments made";
    }

    public String getPreviousPaymentInterest(String botResponse){
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        List<Transaction> transactions = getLoansResponse.getTransactions();
        if(!transactions.isEmpty()) {
            Transaction latest = transactions.get(transactions.size()-1);
            return latest.getInterestPortion().toString();
        }
        return "No payments made";
    }

    public String getNextDueDate(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        RepaymentSchedule schedule = getLoansResponse.getRepaymentSchedule();
        List<Period> periods = schedule.getPeriods();
        periods.remove(0);
        for(Period period : periods) {
            if(!period.isComplete()) {
                return helper.getDate(period.getDueDate());
            }
        }
        return "No due items";
    }

    public String getNextDuePrincipal(String botResponse){
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        RepaymentSchedule schedule = getLoansResponse.getRepaymentSchedule();
        List<Period> periods = schedule.getPeriods();
        periods.remove(0);
        for(Period period : periods) {
            if(!period.isComplete()) {
                return String.valueOf(period.getTotalOutstandingForPeriod());
            }
        }
        return "No due items";
    }

    public String getArrearDays(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        Delinquent delinquent = getLoansResponse.getDelinquent();
        if(delinquent.getDelinquentDate() != null) {
            return helper.getDate(delinquent.getDelinquentDate());
        }
        return "No arrears";
    }

    public String getLoanDisbursedDate(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        Timeline time = getLoansResponse.getTimeline();
        List<Integer> dateList = time.getActualDisbursementDate();
        return helper.getDate(dateList);
    }

    public String getLoanApprovedDate(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        Timeline time = getLoansResponse.getTimeline();
        List<Integer> dateList = time.getApprovedOnDate();
        return helper.getDate(dateList);
    }

    public String getFirstRepaymentDate(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        RepaymentSchedule schedule = getLoansResponse.getRepaymentSchedule();
        List<Period> periods = schedule.getPeriods();
        periods.remove(0);
        return helper.getDate(periods.get(0).getDueDate());
    }

    public String getClientActivationDate(String botResponse) {
        GetClientInfoResponse response = fineractService.getClientInfo();
        List<Integer> date = response.getActivationDate();
        return helper.getDate(date);
    }
}
