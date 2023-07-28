package org.mifos.chatbot.server.config.openFeign;

import org.mifos.chatbot.server.request.PostAuthenticationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="fineract-provider", url = "https://gsoc.mifos.community/fineract-provider/api")
@Component
public interface FineractServiceOpenFeign {

    @PostMapping(value = "/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> authenticate(@RequestBody PostAuthenticationRequest request, @RequestParam String tenantIdentifier);

    @GetMapping(value = "/v1/clients/{clientId}/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getClient(@PathVariable Integer clientId, @RequestParam String tenantIdentifier,
                                     @RequestHeader("Authorization") String authorization);

    @GetMapping(value="/v1/loans/{loanId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getLoans(@PathVariable Integer loanId, @RequestParam String associations,
                                    @RequestParam String exclude, @RequestParam String tenantIdentifier,
                                    @RequestHeader("Authorization") String authorization);
}
