package com.valychbreak.moneytransfer.http;

import lombok.Getter;

public class ResponseError {
    @Getter
    private final String error;

    public ResponseError(Exception exception) {
        this.error = exception.getMessage();
    }
}
