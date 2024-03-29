package com.valychbreak.moneytransfer.http;

public class ResponseError extends ResponseEntity<ErrorEntity> {

    private ResponseError(ErrorEntity errorEntity) {
        super(errorEntity);
    }

    public static ResponseError of(String errorDescription) {
        ErrorEntity errorEntity = new ErrorEntity("Invalid request", errorDescription);
        return new ResponseError(errorEntity);
    }

    public static ResponseError of(Exception exception) {
        ErrorEntity errorEntity = new ErrorEntity("Invalid request", exception.getMessage());
        return new ResponseError(errorEntity);
    }
}
