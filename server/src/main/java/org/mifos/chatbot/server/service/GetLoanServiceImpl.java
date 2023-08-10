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
    public String getDisbursementDate(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        Timeline time = getLoansResponse.getTimeline();
        List<Integer> dateList = time.getExpectedDisbursementDate();
        return helper.getDate(dateList);
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

    public String getNextDueDate(String botResponse) {
        GetLoansResponse getLoansResponse = fineractService.getLoanDetails(botResponse);
        RepaymentSchedule schedule = getLoansResponse.getRepaymentSchedule();
        List<Period> periods = schedule.getPeriods();
        for(Period period : periods) {
            if(!period.isComplete()) {
                return helper.getDate(period.getDueDate());
            }
        }
        return "No due items";
    }
}
