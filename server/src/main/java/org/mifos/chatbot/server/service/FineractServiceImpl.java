package org.mifos.chatbot.server.service;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import okhttp3.*;
import org.mifos.chatbot.server.config.DisableCertificateValidation;
import org.mifos.chatbot.server.config.openFeign.Response.DisbursementDetail;
import org.mifos.chatbot.server.config.openFeign.Response.GetLoansResponse;
import org.mifos.chatbot.server.model.LoanAccount;
import org.mifos.chatbot.server.model.LoanAccounts;
//import org.mifos.chatbot.server.repository.ClientRepository;
import org.mifos.chatbot.server.config.openFeign.FineractServiceOpenFeign;
import org.mifos.chatbot.server.model.Client;
import org.mifos.chatbot.server.model.Tracker;
import org.mifos.chatbot.server.request.PostAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;


@Service
public class FineractServiceImpl {

//   @Autowired
//   private ClientRepository clientRepository;
   @Autowired
   private FineractServiceOpenFeign openFeign;

    public void authorization(String text) {
        String[] splitString = text.split("\\:");

        if (splitString.length >= 2) {
            String adminUsername = splitString[1];
            String adminPassword = splitString[2];
            String clientUsername = splitString[3];
            PostAuthenticationRequest request = PostAuthenticationRequest.builder()
                    .username(adminUsername)
                    .password(adminPassword)
                    .build();
            // TODO check if tenantIdentifier will always be default
            try {
                DisableCertificateValidation.disable();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            }
            ResponseEntity feignResponse = openFeign.authenticate
                    (request, "gsoc");

            if(feignResponse.getStatusCode().value() == 200) {
                Client client = Client.builder()
                        .id(2)
                        .adminUsername(adminUsername)
                        .adminPassword(adminPassword)
                        .clientUsername(clientUsername)
                        .build();
                //clientRepository.save(client);
            }
            else {

            }
        }
    }

    public LoanAccounts getClientDetails() {
        String admin_username = "mifos";
        String admin_password = "password";
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
        String admin_username = "mifos";
        String admin_password = "password";
        String basicAuthCredentials = "Basic " + Base64.getEncoder().encodeToString((admin_username + ":" + admin_password).getBytes());
        ResponseEntity feignResponse = openFeign.getLoans(1, "all", "guarantors,futureSchedule",
                "gsoc", basicAuthCredentials);
        if(feignResponse.getStatusCode().value() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GetLoansResponse loansResponse = objectMapper.readValue(feignResponse.getBody().toString(), GetLoansResponse.class);
                return loansResponse;
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
