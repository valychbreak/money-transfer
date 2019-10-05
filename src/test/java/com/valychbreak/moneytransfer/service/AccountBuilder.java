package com.valychbreak.moneytransfer.service;

import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.domain.Balance;

import java.math.BigDecimal;

public class AccountBuilder {
    private Long id;
    private Balance balance;

    private AccountBuilder() { }

    public static AccountBuilder anAccount() {
        return new AccountBuilder();
    }

    public AccountBuilder withId(Long id) {
        this.id = id;
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
        return new Account(id, balance);
    }
}
