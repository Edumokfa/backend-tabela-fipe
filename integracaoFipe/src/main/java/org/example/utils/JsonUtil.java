package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

public class JsonUtil {

    public static String convertToJson(Collection collection) {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = null;
        try {
            response = objectMapper.writeValueAsString(collection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}
