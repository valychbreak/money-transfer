package com.valychbreak.moneytransfer.service;

import com.valychbreak.moneytransfer.domain.Account;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class AccountAssetService {
    public void transfer(Account sender, Account receiver, BigDecimal amount) {
        if (sender.equals(receiver)) {
            throw new IllegalArgumentException("Sender and Receiver accounts cannot be the same");
        }

        if (amount.compareTo(ZERO) < 0 || amount.equals(ZERO)) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        sender.getBalance().credit(amount);
        receiver.getBalance().debit(amount);
    }
}
