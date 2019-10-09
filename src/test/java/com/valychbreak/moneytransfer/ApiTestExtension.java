package com.valychbreak.moneytransfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.valychbreak.moneytransfer.config.MainModule;
import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import spark.Spark;

public class ApiTestExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback {

    private Injector injector;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        RestAssured.baseURI = "http://localhost:8181/";

        SparkApplicationLauncher sparkApplicationLauncher = new SparkApplicationLauncher(8181);
        sparkApplicationLauncher.launch();
        Spark.awaitInitialization();

        injector = sparkApplicationLauncher.getInjector();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        injector.injectMembers(extensionContext.getRequiredTestInstance());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        Spark.stop();
    }
}
