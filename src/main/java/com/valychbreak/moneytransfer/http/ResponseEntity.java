package com.valychbreak.moneytransfer.http;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ResponseEntity<T> {
    @Getter
    private final T data;

    ResponseEntity() {
        this.data = null;
    }

    ResponseEntity(T data) {
        this.data = data;
    }

    public static <T> ResponseEntity<T> of(T data) {
        return new ResponseEntity<>(data);
    }

    public static ResponseEntity empty() {
        return new ResponseEntity();
    }
}
