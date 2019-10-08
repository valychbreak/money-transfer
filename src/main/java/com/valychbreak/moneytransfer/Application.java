package com.valychbreak.moneytransfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.valychbreak.moneytransfer.controller.AssetTransferController;
import com.valychbreak.moneytransfer.exception.RequestException;
import com.valychbreak.moneytransfer.http.ResponseError;
import lombok.extern.slf4j.Slf4j;

import static spark.Spark.*;

@Slf4j
public class Application {
    public static void main(String[] args) {
        port(8080);

        Injector injector = Guice.createInjector(new MainModule());
        establishRoutes(injector);
    }

    static void establishRoutes(Injector injector) {
        final AssetTransferController assetTransferController = injector.getInstance(AssetTransferController.class);
        final ObjectMapper objectMapper = new ObjectMapper();

        post("/transfer", "application/json", assetTransferController::handle);

        after((req, res) -> res.type("application/json"));

        exception(RequestException.class, (exception, req, res) -> {
            res.status(400);
            res.type("application/json");
            ResponseError responseError = new ResponseError(exception);
            try {
                String errorAsJson = objectMapper.writeValueAsString(responseError);
                res.body(errorAsJson);
            } catch (JsonProcessingException e) {
                log.error("Could not construct JSON of response error: [{}]", responseError, e);
            }
        });
    }
}
