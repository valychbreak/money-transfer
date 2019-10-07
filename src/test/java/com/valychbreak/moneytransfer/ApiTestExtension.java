package com.valychbreak.moneytransfer;

import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import spark.Spark;

public class ApiTestExtension implements BeforeAllCallback, AfterAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        Spark.port(8181);
        RestAssured.baseURI = "http://localhost:8181/";
        Application.establishRoutes();
        Spark.awaitInitialization();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        Spark.stop();
    }
}
