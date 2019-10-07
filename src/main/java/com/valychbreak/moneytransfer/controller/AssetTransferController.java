package com.valychbreak.moneytransfer.controller;

import com.valychbreak.moneytransfer.exception.RequestException;
import spark.Request;
import spark.Response;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class AssetTransferController {

    public String handle(Request request, Response response) throws RequestException {
        String senderAccountNumber =
                getParamValue(request, "sender_account")
                        .orElseThrow(() -> new RequestException("'sender_account' param is not specified"));

        String receiverAccountNumber =
                getParamValue(request, "receiver_account")
                        .orElseThrow(() -> new RequestException("'receiver_account' param is not specified"));

        String assetAmountValue =
                getParamValue(request, "asset_amount")
                        .orElseThrow(() -> new RequestException("'asset_amount' param is not specified"));

        response.type("application/json");
        return "{\"status\": \"Hello, world!\"}";
    }

    public Optional<String> getParamValue(Request request, String paramName) {
        return Optional.ofNullable(request.queryParams(paramName));
    }
}
