package com.valychbreak.moneytransfer.controller;

import com.google.inject.persist.Transactional;
import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.exception.RequestException;
import com.valychbreak.moneytransfer.http.ResponseEntity;
import com.valychbreak.moneytransfer.repository.AccountRepository;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountController extends AbstractController {
    private AccountRepository accountRepository;

    @Inject
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    protected ResponseEntity<?> doHandle(Request request, Response response) throws RequestException {
        String accountNumber = request.queryParams("account_number");
        if (accountNumber == null) {
            throw new RequestException("account_number is not provided");
        }

        try {
            Account account = accountRepository.findByAccountNumber(accountNumber);
            return ResponseEntity.of(account);
        } catch (Exception e) {
            throw new RequestException("Invalid account number");
        }
    }
}
