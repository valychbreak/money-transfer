package com.valychbreak.moneytransfer.controller;

import com.google.inject.persist.Transactional;
import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.dto.AssetTransferDto;
import com.valychbreak.moneytransfer.exception.DataValidationException;
import com.valychbreak.moneytransfer.exception.RequestException;
import com.valychbreak.moneytransfer.http.ResponseEntity;
import com.valychbreak.moneytransfer.repository.AccountRepository;
import com.valychbreak.moneytransfer.service.AccountAssetService;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Singleton
public class AssetTransferController extends AbstractController {

    private final AccountRepository accountRepository;
    private final AccountAssetService accountAssetService;

    @Inject
    AssetTransferController(AccountRepository accountRepository, AccountAssetService accountAssetService) {
        this.accountRepository = accountRepository;
        this.accountAssetService = accountAssetService;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    protected ResponseEntity<?> doHandle(Request request, Response response) throws RequestException {

        AssetTransferDto assetTransferDto = getAssetTransferDto(request);

        Account senderAccount = findAccount(assetTransferDto.getSenderAccountNumber());
        Account receiverAccount = findAccount(assetTransferDto.getReceiverAccountNumber());

        try {
            accountAssetService.transfer(senderAccount, receiverAccount, assetTransferDto.getTransferAmount());
        } catch (DataValidationException e) {
             log.error("Failed to transfer assets. Transfer data: {}", assetTransferDto, e);
            throw new RequestException(e.getMessage());
        }

        log.info("Successfully transferred assets. Transfer data: {}", assetTransferDto);
        return ResponseEntity.empty();
    }

    private AssetTransferDto getAssetTransferDto(Request request) throws RequestException {
        String senderAccountNumber =
                getParamValue(request, "sender_account")
                        .orElseThrow(() -> new RequestException("'sender_account' param is not specified"));

        String receiverAccountNumber =
                getParamValue(request, "receiver_account")
                        .orElseThrow(() -> new RequestException("'receiver_account' param is not specified"));

        String assetAmountValue =
                getParamValue(request, "asset_amount")
                        .orElseThrow(() -> new RequestException("'asset_amount' param is not specified"));

        BigDecimal transferAmount;
        try {
            transferAmount = new BigDecimal(assetAmountValue);
        } catch (NumberFormatException e) {
            throw new RequestException("'asset_amount' param must be a number");
        }

        return new AssetTransferDto(senderAccountNumber, receiverAccountNumber, transferAmount);
    }

    private Account findAccount(String senderAccountNumber) throws RequestException {
        try {
            return accountRepository.findByAccountNumber(senderAccountNumber);
        } catch (NoResultException e) {
            throw new RequestException(String.format("Account number '%s' does not exist", senderAccountNumber));
        }
    }

    private Optional<String> getParamValue(Request request, String paramName) {
        return Optional.ofNullable(request.queryParams(paramName));
    }
}
