package com.valychbreak.moneytransfer.routes;

import com.valychbreak.moneytransfer.Application;
import com.valychbreak.moneytransfer.domain.Account;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import spark.Spark;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

class AssetTransferRouteTest {
    @BeforeAll
    static void beforeAll() {
        Spark.port(8181);
        RestAssured.baseURI = "http://localhost:8181/";
        Application.establishRoutes();
        Spark.awaitInitialization();
    }

    @AfterAll
    static void afterAll() {
        Spark.stop();
    }

    @Test
    void shouldReturnStatusOk() {
        given().urlEncodingEnabled(true)
                .accept(ContentType.URLENC)
            .when()
                .post("/transfer")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("Hello, world!"));
    }

    @ParameterizedTest
    @EnumSource(
            value = ContentType.class,
            names = {"ANY", "URLENC"},
            mode = EXCLUDE)
    void shouldNotAcceptAnyButUrlEncodedData(ContentType contentType) {
        given().accept(contentType)
            .when()
                .post("/transfer")
            .then()
                .statusCode(404);
    }
}
