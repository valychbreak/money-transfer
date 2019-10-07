package com.valychbreak.moneytransfer;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.valychbreak.moneytransfer.controller.AssetTransferController;
import com.valychbreak.moneytransfer.controller.ControllerModule;
import lombok.extern.java.Log;

import static spark.Spark.port;
import static spark.Spark.post;

@Log
public class Application {
    public static void main(String[] args) {
        port(8080);

        establishRoutes();
    }

    static void establishRoutes() {
        Injector injector = Guice.createInjector(new ControllerModule());
        AssetTransferController assetTransferController = injector.getInstance(AssetTransferController.class);

        post("/transfer", "application/json", assetTransferController::handle);
    }
}
