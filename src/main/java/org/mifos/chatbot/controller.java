package org.mifos.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/")
public class controller {

    @Autowired
    AuthenticationProxy authenticationProxy;
    @GetMapping(value = "/qbpost", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Service is running.", HttpStatus.OK);
    }

    @GetMapping("/feignclient")
    public  ResponseEntity<String> your_client_controller_api() {
        return authenticationProxy.test();
    }
}
