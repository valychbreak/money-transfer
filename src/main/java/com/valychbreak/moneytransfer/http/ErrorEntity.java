package com.valychbreak.moneytransfer.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

class ErrorEntity {
    @Getter
    private final String error;

    @Getter
    @JsonProperty(value = "error_description")
    private final String errorDescription;

    ErrorEntity(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }
}
