package com.valychbreak.moneytransfer.service;

import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.domain.Balance;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class AccountBuilder {
    private String number;
    private Balance balance;

    private AccountBuilder() { }

    public static AccountBuilder anAccount() {
        return new AccountBuilder().withBalance(0);
    }

    public static AccountBuilder aRandomAccount() {
        return new AccountBuilder()
                .withRandomValidNumber()
                .withBalance(RandomUtils.nextInt(100, 10000));
    }

    public AccountBuilder withRandomValidNumber() {
        this.number = randomNumeric(26);
        return this;
    }

    public AccountBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public AccountBuilder withBalance(Balance balance) {
        this.balance = balance;
        return this;
    }

    public AccountBuilder withBalance(int amount) {
        this.balance = Balance.of(new BigDecimal(amount));
        return this;
    }

    public Account build() {
        return new Account(null, number, balance);
    }
}
