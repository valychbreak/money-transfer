package com.valychbreak.moneytransfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.valychbreak.moneytransfer.config.MainModule;
import com.valychbreak.moneytransfer.controller.AccountController;
import com.valychbreak.moneytransfer.controller.AssetTransferController;
import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.domain.Balance;
import com.valychbreak.moneytransfer.exception.RequestException;
import com.valychbreak.moneytransfer.http.HttpContentType;
import com.valychbreak.moneytransfer.http.HttpStatus;
import com.valychbreak.moneytransfer.http.ResponseError;
import com.valychbreak.moneytransfer.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static com.valychbreak.moneytransfer.utils.JsonConverter.json;
import static spark.Spark.*;

@Slf4j
public class Application {
    public static void main(String[] args) {
        port(8080);

        Injector injector = Guice.createInjector(new MainModule());

        // For testing purpose only
        createTestAccounts(injector);

        establishRoutes(injector);
    }

    private static void createTestAccounts(Injector injector) {
        AccountRepository accountRepository = injector.getInstance(AccountRepository.class);

        Account accountOne = new Account();
        accountOne.setNumber("19806578940000111122223333");
        accountOne.setBalance(Balance.of(new BigDecimal(1_000_000)));

        Account accountTwo = new Account();
        accountTwo.setNumber("19806578940000999988887777");
        accountTwo.setBalance(Balance.of(new BigDecimal(1_000_000)));

        accountRepository.create(accountOne);
        accountRepository.create(accountTwo);
    }

    static void establishRoutes(Injector injector) {
        final AssetTransferController assetTransferController = injector.getInstance(AssetTransferController.class);
        final AccountController accountController = injector.getInstance(AccountController.class);

        post("/transfer", HttpContentType.APPLICATION_JSON, assetTransferController::handle);

        // For testing purpose only
        get("/account", HttpContentType.APPLICATION_JSON, accountController::handle);

        after((req, res) -> res.type(HttpContentType.APPLICATION_JSON));

        notFound(((req, res) -> {
            ResponseError responseError = ResponseError.of("Endpoint with requested parameters does not exist");
            res.status(HttpStatus.BAD_REQUEST);
            return json(responseError);
        }));

        exception(RequestException.class, (exception, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST);
            res.type(HttpContentType.APPLICATION_JSON);

            ResponseError responseError = ResponseError.of(exception);

            try {
                String errorAsJson = json(responseError);
                res.body(errorAsJson);
            } catch (JsonProcessingException e) {
                log.error("Could not construct JSON of response error: [{}]", responseError, e);
                res.body("{ \"data\": {\"error\":\"Failed to construct response entity\" }");
            }
        });
    }
}
