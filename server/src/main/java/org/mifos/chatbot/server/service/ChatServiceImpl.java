package org.mifos.chatbot.server.service;

import com.google.gson.JsonObject;
import okhttp3.*;
import org.mifos.chatbot.server.model.Intent;
import org.mifos.chatbot.server.model.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatServiceImpl {
    @Value("${RASA_SERVER}")
    private String RASA_SERVER;
    @Value("${WEBHOOK}")
    private String WEBHOOK;
    Helper helper = new Helper();
    private static final String LOGIN_CREDENTIALS = "login_credentials";
    private static final String LOAN_STATUS = "loan_status";
    private static final String DISBURSEMENT_AMOUNT = "disbursement_amount";
    private static final String MATURITY_DATE = "maturity_date";
    private static final String NEXT_DUE_DATE = "next_due_date";
    private static final String NEXT_DUE_PRINCIPAL = "next_due_principal";
    private static final String APPROVED_PRINCIPAL = "approved_principal";
    private static final String INTEREST_RATE = "interest_rate";
    private static final String PREVIOUS_PAYMENT_DATE = "previous_payment_date";
    private static final String PREVIOUS_PAYMENT_AMOUNT = "previous_payment_amount";
    private static final String PREVIOUS_PAYMENT_INTEREST = "previous_payment_interest";
    private static final String ARREAR_DAY = "arrear_day";
    private static final String LOAN_DISBURSED_DATE = "loan_disbursed_date";
    private static final String LOAN_APPROVED_DATE = "loan_approved_date";
    private static final String FIRST_REPAYMENT_DATE = "first_repayment_date";
    private static final String CLIENT_ACTIVATION_DATE = "client_activation_date";

    @Autowired
    private GetLoanServiceImpl loanService;

    public String processUserUtterance(String botResponse, String conversationId) throws IOException {
        Tracker tracker = retriveConversationTracker(conversationId);
        Intent intent = findIntent(tracker);
        return classifyIntent(botResponse, intent, tracker);
    }
    public String getResponse(String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        System.out.println("JSON Payload: " + helper.createJSONRequest(message));
        RequestBody body = RequestBody.create(mediaType, helper.createJSONRequest(message));
        Request request = new Request.Builder()
                .url("http://localhost:5005/webhooks/rest/webhook")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() != 200) {
            //TODO handle exception
        }
        return response.message();
    }

    public String classifyIntent(String botResponse, Intent intent, Tracker tracker) {
        String intentName = intent.getName();
//        if(intentName.equals(LOGIN_CREDENTIALS)) {
//            loanServic.authorization(tracker.getLatestMessage().getText());
//        }
        if(intentName.equals(LOAN_STATUS)) {
            return loanService.getLoanStatus(botResponse, tracker);
        }
        else if(intentName.equals(APPROVED_PRINCIPAL)) {
            return loanService.getApprovedPrincipalAmount(botResponse).toString();
        }
        else if(intentName.equals(INTEREST_RATE)) {
            return loanService.getInterestRate(botResponse).toString();
        }
        else if(intentName.equals(MATURITY_DATE)) {
            return loanService.getMaturityDate(botResponse);
        }
        else if(intentName.equals(NEXT_DUE_DATE)) {
            return loanService.getNextDueDate(botResponse);
        }
        else if(intentName.equals(NEXT_DUE_PRINCIPAL)) {
            return loanService.getNextDuePrincipal(botResponse);
        }
        else if(intentName.equals(PREVIOUS_PAYMENT_DATE)) {
            return loanService.getPreviousPaymentDate(botResponse);
        }
        else if(intentName.equals(PREVIOUS_PAYMENT_AMOUNT)) {
            return loanService.getPreviousPaymentAmount(botResponse);
        }
        else if(intentName.equals(PREVIOUS_PAYMENT_INTEREST)) {
            return loanService.getPreviousPaymentInterest(botResponse);
        }
        else if(intentName.equals(ARREAR_DAY)) {
            return loanService.getArrearDays(botResponse);
        }
        else if(intentName.equals(LOAN_DISBURSED_DATE)) {
            return loanService.getLoanDisbursedDate(botResponse);
        }
        else if(intentName.equals(LOAN_APPROVED_DATE)) {
            return loanService.getLoanApprovedDate(botResponse);
        }
        else if(intentName.equals(FIRST_REPAYMENT_DATE)) {
            return loanService.getFirstRepaymentDate(botResponse);
        }
        else if(intentName.equals(CLIENT_ACTIVATION_DATE)){
            return loanService.getClientActivationDate(botResponse);
        }

        return botResponse;
    }

    public Tracker retriveConversationTracker(String conversationId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(RASA_SERVER + "/conversations/" + conversationId + "/tracker")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() != 200) {
            //TODO add logging
        }
        JsonObject obj = helper.createJSON(response.body().string());
        Tracker tracker = helper.createTrackerPOJO(obj);
        return tracker;
    }

    public Intent findIntent(Tracker tracker) {
        return tracker.getLatestMessage().getIntent();
    }
}
