package com.valychbreak.moneytransfer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valychbreak.moneytransfer.exception.RequestException;
import com.valychbreak.moneytransfer.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

@Slf4j
public abstract class AbstractController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public final String handle(Request request, Response response) throws RequestException {
        ResponseEntity<?> responseEntity = doHandle(request, response);
        return convertToJson(responseEntity);
    }

    protected abstract ResponseEntity<?> doHandle(Request request, Response response) throws RequestException;

    private String convertToJson(ResponseEntity<?> responseEntity) throws RequestException {
        try {
            return objectMapper.writeValueAsString(responseEntity);
        } catch (JsonProcessingException e) {
            log.error("Could not construct json from ResponseEntity: [{}]", responseEntity, e);
            throw new RequestException("Failed to convert response entity to JSON", e);
        }
    }
}
