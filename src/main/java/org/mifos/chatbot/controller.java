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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping(path = "/")
public class controller {

    @Value("${RASA_SERVER}")
    private String RASA_SERVER;

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
                .url(RASA_SERVER)
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() == 200) {
            String result = createResponse(response.body().string());
            return result;
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

    private String createResponse(String botResponse) {
        StringBuilder responseBuilder = new StringBuilder();
        String pattern = "\"(text|image)\":\"(.*?)\"";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(botResponse);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            if (key.equals("text")) {
                responseBuilder.append(value).append("\n");
            } else if (key.equals("image")) {
                responseBuilder.append(" [Image: ").append(value).append("]\n");
            }
        }
        return responseBuilder.toString();
    }
}
