package com.valychbreak.moneytransfer.controller;

import com.google.inject.persist.Transactional;
import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.exception.RequestException;
import com.valychbreak.moneytransfer.repository.AccountRepository;
import com.valychbreak.moneytransfer.service.AccountAssetService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Optional;

@Singleton
public class AssetTransferController {

    private final AccountRepository accountRepository;
    private final AccountAssetService accountAssetService;

    @Inject
    AssetTransferController(AccountRepository accountRepository, AccountAssetService accountAssetService) {
        this.accountRepository = accountRepository;
        this.accountAssetService = accountAssetService;
    }

    @Transactional
    public String handle(Request request, Response response) throws RequestException {
        String senderAccountNumber =
                getParamValue(request, "sender_account")
                        .orElseThrow(() -> new RequestException("'sender_account' param is not specified"));

        String receiverAccountNumber =
                getParamValue(request, "receiver_account")
                        .orElseThrow(() -> new RequestException("'receiver_account' param is not specified"));

        String assetAmountValue =
                getParamValue(request, "asset_amount")
                        .orElseThrow(() -> new RequestException("'asset_amount' param is not specified"));

        Account senderAccount = accountRepository.findByAccountNumber(senderAccountNumber);
        Account receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber);
        accountAssetService.transfer(senderAccount, receiverAccount, new BigDecimal(assetAmountValue));

        response.type("application/json");
        return "{}";
    }

    public Optional<String> getParamValue(Request request, String paramName) {
        return Optional.ofNullable(request.queryParams(paramName));
    }
}
