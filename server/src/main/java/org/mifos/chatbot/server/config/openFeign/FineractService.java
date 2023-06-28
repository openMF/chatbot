package org.mifos.chatbot.server.config.openFeign;

import org.mifos.chatbot.server.request.PostAuthenticationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="fineract-provider", url = "https://localhost:8080/fineract-provider/api")
@Component
public interface FineractService {
    @PostMapping(value = "/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody PostAuthenticationRequest request, @RequestParam String tenantIdentifier);
}
