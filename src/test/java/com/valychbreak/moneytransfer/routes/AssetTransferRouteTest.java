package com.valychbreak.moneytransfer.routes;

import com.valychbreak.moneytransfer.ApiTestExtension;
import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.domain.Balance;
import com.valychbreak.moneytransfer.http.HttpStatus;
import com.valychbreak.moneytransfer.repository.AccountRepository;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.inject.Inject;
import java.math.BigDecimal;

import static com.valychbreak.moneytransfer.service.AccountBuilder.anAccount;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

@ExtendWith(ApiTestExtension.class)
class AssetTransferRouteTest {

    private static final String SENDER_ACCOUNT_NUMBER = RandomStringUtils.randomAlphabetic(Account.ACCOUNT_NUMBER_LENGTH);
    private static final String RECEIVER_ACCOUNT_NUMBER = RandomStringUtils.randomAlphabetic(Account.ACCOUNT_NUMBER_LENGTH);

    @Inject
    private AccountRepository accountRepository;

    @Test
    void shouldTransferAssetsFromSenderToReceiver() {

        Account senderAccount = anAccount()
                .withNumber(SENDER_ACCOUNT_NUMBER)
                .withBalance(5000)
                .build();

        Account receiverAccount = anAccount()
                .withNumber(RECEIVER_ACCOUNT_NUMBER)
                .withBalance(100)
                .build();

        accountRepository.create(senderAccount);
        accountRepository.create(receiverAccount);

        given()
                .urlEncodingEnabled(true)
                .param("sender_account", SENDER_ACCOUNT_NUMBER)
                .param("receiver_account", RECEIVER_ACCOUNT_NUMBER)
                .param("asset_amount", BigDecimal.TEN)
                .accept(ContentType.JSON)
            .when()
                .post("/transfer")
            .then()
                .statusCode(HttpStatus.OK)
                .contentType(ContentType.JSON);

        senderAccount = accountRepository.findByAccountNumber(SENDER_ACCOUNT_NUMBER);
        receiverAccount = accountRepository.findByAccountNumber(RECEIVER_ACCOUNT_NUMBER);

        assertThat(senderAccount.getBalance()).isEqualTo(Balance.of(new BigDecimal(4990)));
        assertThat(receiverAccount.getBalance()).isEqualTo(Balance.of(new BigDecimal(110)));
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
                .statusCode(HttpStatus.BAD_REQUEST)
                .body("data.error", equalTo("Invalid request"))
                .body("data.error_description", equalTo("Endpoint with requested parameters does not exist"));
    }

    @Test
    void shouldReturnErrorWhenSenderIsNotSpecified() {
        given()
                .accept(ContentType.JSON)
            .when()
                .post("/transfer")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST)
                .body("data.error", equalTo("Invalid request"))
                .body("data.error_description", equalTo("'sender_account' param is not specified"));
    }

    @Test
    void shouldReturnErrorWhenReceiverIsNotSpecified() {
        given()
                .urlEncodingEnabled(true)
                .param("sender_account", SENDER_ACCOUNT_NUMBER)
                .accept(ContentType.JSON)
            .when()
                .post("/transfer")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST)
                .body("data.error", equalTo("Invalid request"))
                .body("data.error_description", equalTo("'receiver_account' param is not specified"));
    }

    @Test
    void shouldReturnErrorWhenAmountIsNotSpecified() {
        given()
                .urlEncodingEnabled(true)
                .param("sender_account", SENDER_ACCOUNT_NUMBER)
                .param("receiver_account", RECEIVER_ACCOUNT_NUMBER)
                .accept(ContentType.JSON)
            .when()
                .post("/transfer")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST)
                .body("data.error", equalTo("Invalid request"))
                .body("data.error_description", equalTo("'asset_amount' param is not specified"));
    }
}
