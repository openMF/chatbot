package org.mifos.chatbot.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mifos.chatbot.server.config.DisableCertificateValidation;
import org.mifos.chatbot.server.config.openFeign.Response.GetClientInfoResponse;
import org.mifos.chatbot.server.config.openFeign.Response.GetLoansResponse;
import org.mifos.chatbot.server.model.LoanAccounts;
import org.mifos.chatbot.server.config.openFeign.FineractServiceOpenFeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Service
public class FineractServiceImpl {

   @Autowired
   private FineractServiceOpenFeign openFeign;

    String admin_username = "mifos";
    String admin_password = "password";

    public LoanAccounts getClientDetails() {
        LoanAccounts loanAccounts = new LoanAccounts();
        try {
            DisableCertificateValidation.disable();
            String basicAuthCredentials = "Basic " + Base64.getEncoder().encodeToString((admin_username + ":" + admin_password).getBytes());
            ResponseEntity feignResponse = openFeign.getClient(1, "gsoc", basicAuthCredentials);
            if(feignResponse.getStatusCode().value() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    loanAccounts = objectMapper.readValue(feignResponse.getBody().toString(), LoanAccounts.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return loanAccounts;
    }

    public GetLoansResponse getLoanDetails(String response) {
        String basicAuthCredentials = "Basic " + Base64.getEncoder().encodeToString((admin_username + ":" + admin_password).getBytes());
        ResponseEntity feignResponse = openFeign.getLoans(1, "all", "guarantors,futureSchedule",
                "gsoc", basicAuthCredentials);
        if(feignResponse.getStatusCode().value() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(feignResponse.getBody().toString(), GetLoansResponse.class);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public GetClientInfoResponse getClientInfo() {
        String basicAuthCredentials = "Basic " + Base64.getEncoder().encodeToString((admin_username + ":" + admin_password).getBytes());
        ResponseEntity feignResponse = openFeign.getClientInfo(1,"gsoc", basicAuthCredentials);
        if(feignResponse.getStatusCode().value() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(feignResponse.getBody().toString(), GetClientInfoResponse.class);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
