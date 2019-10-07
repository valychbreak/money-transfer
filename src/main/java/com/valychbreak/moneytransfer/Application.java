package com.valychbreak.moneytransfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.valychbreak.moneytransfer.controller.AssetTransferController;
import com.valychbreak.moneytransfer.exception.RequestException;
import lombok.extern.java.Log;

import static spark.Spark.*;

@Log
public class Application {
    public static void main(String[] args) {
        port(8080);

        Injector injector = Guice.createInjector(new MainModule());
        establishRoutes(injector);
    }

    static void establishRoutes(Injector injector) {
        AssetTransferController assetTransferController = injector.getInstance(AssetTransferController.class);

        post("/transfer", "application/json", assetTransferController::handle);

        after((req, res) -> res.type("application/json"));

        exception(RequestException.class, (exception, req, res) -> {
            res.status(400);
            res.type("application/json");
            res.body("{\"error\": \"" + exception.getMessage() + "\"}");
        });
    }
}
