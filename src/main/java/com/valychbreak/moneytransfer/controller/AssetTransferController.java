package com.valychbreak.moneytransfer.controller;

import spark.Request;
import spark.Response;

import javax.inject.Singleton;

@Singleton
public class AssetTransferController {

    public String handle(Request request, Response response) {
        response.type("application/json");
        return "{\"status\": \"Hello, world!\"}";
    }
}
