package org.mifos.chatbot.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import okhttp3.ResponseBody;
import org.mifos.chatbot.server.model.Intent;
import org.mifos.chatbot.server.model.LatestMessage;
import org.mifos.chatbot.server.model.Tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public JsonObject createJSON(String string) {
        return new JsonParser().parse(string).getAsJsonObject();
    }


    public void parseInput(ResponseBody body) {

    }

    public String createJSONRequest(String string) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode();

        jsonNode.put("message", string);

        return mapper.writeValueAsString(jsonNode);
    }

    public String createResponse(String botResponse) {
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

    public Tracker createTrackerPOJO(JsonObject obj) {
        Tracker tracker = new Tracker();
        obj.keySet().forEach(
                key->{
                    if(key.equals("sender_id"))
                        tracker.setConversationId(obj.get("sender_id").toString());
                    else if(key.equals("slots"))
                        tracker.setSlots(null);
                    else if(key.equals("latest_message"))
                        tracker.setLatestMessage(constructLatestMessage(obj.get("latest_message")));
                    else if(key.equals("latest_event_time"))
                        tracker.setLatestEventTime(null);
                    else if(key.equals("followup_action"))
                        tracker.setFollowupAction(obj.get("followup_action").toString());
                    else if(key.equals("paused"))
                        tracker.setPaused(obj.get("paused").getAsBoolean());
//                    else if(key.equals("events"))
//                        tracker.set(obj.get("").toString());
                    else if(key.equals("latest_input_channel"))
                        tracker.setLatestInputChannel(obj.get("latest_input_channel").toString());
                    else if(key.equals("active_loop"))
                        tracker.setActiveLoopName(obj.get("active_loop").toString());
                    else if(key.equals("latest_action"))
                        tracker.setLatestAction(null);
                    else if(key.equals("latest_action_name"))
                        tracker.setLatestActionName(obj.get("latest_action_name").toString());

                }
        );

        return tracker;
    }

    private LatestMessage constructLatestMessage(JsonElement obj) {
        JsonObject jsonObject = obj.getAsJsonObject();
        LatestMessage latestMessage = LatestMessage.builder()
                .intent(constructIntents(jsonObject))
                .entity(null)
                .text(jsonObject.get("text").toString())
                .intentRanking(null)
                .build();
        return latestMessage;
    }

    public Intent constructIntents(JsonObject obj) {
        JsonElement ele = obj.get("intent");
        JsonObject a = ele.getAsJsonObject();
        Intent intent = Intent.builder()
                .name(a.get("name").getAsString())
                .confidence(a.get("confidence").getAsDouble())
                .build();
        return intent;
    }
}
