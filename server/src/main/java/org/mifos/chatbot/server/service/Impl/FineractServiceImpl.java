package org.mifos.chatbot.server.service.Impl;

import org.mifos.chatbot.server.request.PostAuthenticationRequest;
import org.mifos.chatbot.server.service.FineractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FineractServiceImpl implements FineractService {

    @Autowired
    org.mifos.chatbot.server.config.openFeign.FineractService fineractService;

    @Override
    public void authorization(String text) {
        String[] splitString = text.split("\\s+");

        if (splitString.length >= 2) {
            // Extract the username and password
            String username = splitString[1];
            String password = splitString[2];

            // TODO add logging
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            PostAuthenticationRequest request = PostAuthenticationRequest.builder()
                    .username(username)
                    .password(password)
                    .build();
            // TODO check if tenantIdentifier will always be default
            ResponseEntity feignResponse = fineractService.authenticate
                    (request, "default");
            if(feignResponse.getStatusCode().value() == 200) {
                //TODO utter an action for login successful

                // TODO any values that need to be saved in db? What are next steps?
            }
            else {

            }
        }
    }

}
