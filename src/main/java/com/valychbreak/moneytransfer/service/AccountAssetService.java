package com.valychbreak.moneytransfer.service;

import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.exception.DataValidationException;
import com.valychbreak.moneytransfer.exception.InsufficientBalanceException;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class AccountAssetService {
    public void transfer(Account sender, Account receiver, BigDecimal amount) throws DataValidationException {
        if (sender.equals(receiver)) {
            throw new DataValidationException("Sender and Receiver accounts cannot be the same");
        }

        if (amount.compareTo(ZERO) < 0 || amount.equals(ZERO)) {
            throw new DataValidationException("Amount must be positive");
        }

        if (sender.getBalance().getAmount().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        sender.getBalance().credit(amount);
        receiver.getBalance().debit(amount);
    }
}
