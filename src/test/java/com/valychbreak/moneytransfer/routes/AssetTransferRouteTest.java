package com.valychbreak.moneytransfer.routes;

import com.valychbreak.moneytransfer.ApiTestExtension;
import com.valychbreak.moneytransfer.domain.Account;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

@ExtendWith(ApiTestExtension.class)
class AssetTransferRouteTest {

    private static final String ACCOUNT_NUMBER = RandomStringUtils.randomAlphabetic(Account.ACCOUNT_NUMBER_LENGTH);

    @Test
    void shouldReturnStatusOk() {
        given()
                .urlEncodingEnabled(true)
                .param("sender_account", ACCOUNT_NUMBER)
                .param("receiver_account", ACCOUNT_NUMBER)
                .param("asset_amount", BigDecimal.TEN)
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

    @Test
    void shouldReturnErrorWhenSenderIsNotSpecified() {
        given()
                .accept(ContentType.JSON)
            .when()
                .post("/transfer")
            .then()
                .statusCode(400)
                .body("error", equalTo("'sender_account' param is not specified"));
    }

    @Test
    void shouldReturnErrorWhenReceiverIsNotSpecified() {
        given()
                .urlEncodingEnabled(true)
                .param("sender_account", ACCOUNT_NUMBER)
                .accept(ContentType.JSON)
            .when()
                .post("/transfer")
            .then()
                .statusCode(400)
                .body("error", equalTo("'receiver_account' param is not specified"));
    }

    @Test
    void shouldReturnErrorWhenAmountIsNotSpecified() {
        given()
                .urlEncodingEnabled(true)
                .param("sender_account", ACCOUNT_NUMBER)
                .param("receiver_account", ACCOUNT_NUMBER)
                .accept(ContentType.JSON)
            .when()
                .post("/transfer")
            .then()
                .statusCode(400)
                .body("error", equalTo("'asset_amount' param is not specified"));
    }
}
