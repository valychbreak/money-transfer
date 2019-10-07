package com.valychbreak.moneytransfer.routes;

import com.valychbreak.moneytransfer.ApiTestExtension;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

@ExtendWith(ApiTestExtension.class)
class AssetTransferRouteTest {

    @Test
    void shouldReturnStatusOk() {
        given()
                .urlEncodingEnabled(true)
                .accept(ContentType.JSON)
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
            names = {"ANY", "JSON"},
            mode = EXCLUDE)
    void shouldNotAcceptAnyButUrlEncodedData(ContentType contentType) {
        given()
                .accept(contentType)
            .when()
                .post("/transfer")
            .then()
                .statusCode(404);
    }
}
