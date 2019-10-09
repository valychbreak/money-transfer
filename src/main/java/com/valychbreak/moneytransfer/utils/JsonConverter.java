package com.valychbreak.moneytransfer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private static final String EMPTY_JSON_OBJECT = "{}";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String json(Object object) throws JsonProcessingException {
        if (object == null) {
            return EMPTY_JSON_OBJECT;
        }
        return OBJECT_MAPPER.writeValueAsString(object);
    }
}
