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

    private static final Injector INJECTOR = Guice.createInjector(new MainModule());

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        Spark.port(8181);
        RestAssured.baseURI = "http://localhost:8181/";

        Application.establishRoutes(INJECTOR);
        Spark.awaitInitialization();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        INJECTOR.injectMembers(extensionContext.getRequiredTestInstance());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        Spark.stop();
    }
}
