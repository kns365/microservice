package com.kns.apps.microservice.configserver.application.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JsonHelper {

    private static ObjectMapper mapper = new ObjectMapper();

    public JsonHelper() {
        this.mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String objectToJson(Object input) {
        String output = null;
        try {
            output = mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            log.error("Try prase jsonToObject {}", e);
        }
        return output;
    }

    public static Object jsonToObject(String input) throws IOException {
        Object output = null;
        output = mapper.readValue(input, Object.class);
        return output;
    }

    public static <T> T jsonToObjectT(String input, T objT) {
        T output = null;
        try {
            output = (T) mapper.readValue(input, objT.getClass());
        } catch (Exception ignore) {
            //log.error("Try prase jsonToObjectT {}", ignore);
        }
        return output;
    }

    public static String getValueFromJsonByKey(String json, String key, boolean removeQuotes) throws IOException {
        String value = "";
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(json);
            value = rootNode.get(key).toString();
        } catch (JsonProcessingException e) {
            log.error("Try getValueFromJsonByKey " + key + " {}", e);
        }
        if (removeQuotes == true) {
//            value = value.replace("\"", ""); //remove "" from value
            //remove "{...}" -> {...}
            if (value.startsWith("\"{")) {
                value = value.substring(1, value.length() - 1);
            }
            if (value.endsWith("}\"")) {
                value = value.substring(0, value.length() - 2);
            }
            /*//remove "[{...}]" -> {...}
            if (value.startsWith("\"[")) {
                value = value.substring(2, value.length() - 1);
            }
            if (value.endsWith("]\"")) {
                value = value.substring(0, value.length() - 3);
            }
            //remove ""200"" -> "200"
            if (value.startsWith("\"\"")) {
                value = value.substring(1, value.length() - 1);
            }
            if (value.endsWith("\"\"")) {
                value = value.substring(0, value.length() - 2);
            }*/
            value = value.replace("\\", ""); //remove \ from value
        }
        return value;
    }


}
