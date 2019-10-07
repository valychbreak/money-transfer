package com.valychbreak.moneytransfer.routes;

import com.valychbreak.moneytransfer.Application;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

import static io.restassured.RestAssured.post;
import static org.hamcrest.Matchers.equalTo;

class AssetTransferRouteTest {
    @BeforeEach
    void setUp() {
        Spark.port(8181);
        RestAssured.baseURI = "http://localhost:8181/";
        Application.establishRoutes();
        Spark.awaitInitialization();
    }

    @AfterEach
    void tearDown() {
        Spark.stop();
    }

    @Test
    void shouldReturnStatusOk() {
        post("/transfer").then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("Hello, world!"));
    }
}
