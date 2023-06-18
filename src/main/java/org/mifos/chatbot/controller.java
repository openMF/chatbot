package org.mifos.chatbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;



@RestController
@RequestMapping(path = "/")
public class controller {

    String rasaEndpoint = "http://localhost:5005/webhooks/rest/webhook";

    @Autowired
    AuthenticationProxy authenticationProxy;

    @GetMapping("/authenticate-user")
    public  ResponseEntity<String> your_client_controller_api() {
        return authenticationProxy.test();
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, createJSONRequest(message));
        Request request = new Request.Builder()
                .url(rasaEndpoint)
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() == 200) {
            String responseBody = response.body().string();
            return responseBody;
        }
        else {
            return "Request failed with status code: " + response.code();
        }
    }

    private String createJSONRequest(String string) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode();

        jsonNode.put("message", string);

        return mapper.writeValueAsString(jsonNode);
    }
}
