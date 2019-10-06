package com.valychbreak.moneytransfer.repository;

import com.valychbreak.moneytransfer.domain.Account;

import javax.persistence.EntityManager;

public class AccountRepository {

    private EntityManager entityManager;

    public AccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account findByAccountNumber(String number) {
        return null;
    }
}
