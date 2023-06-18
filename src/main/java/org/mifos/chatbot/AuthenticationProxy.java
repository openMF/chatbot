package org.mifos.chatbot;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(name="fineract-provider", url = "https://localhost:8443/fineract-provider/")
//public interface AuthenticationProxy {
//    @PostMapping(value = "/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> authenticate(String requestBodyAsJson);
//}


@FeignClient(name="demo-service", url = "localhost:8082")
public interface AuthenticationProxy {
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test();
}