package com.valychbreak.moneytransfer.repository;

import com.valychbreak.moneytransfer.domain.Account;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AccountRepository {

    private EntityManager entityManager;

    public AccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account findByAccountNumber(String number) {
        TypedQuery<Account> accountByNumberQuery = entityManager
                .createQuery("SELECT account From Account account where account.number = :number", Account.class)
                .setParameter("number", number);

        return accountByNumberQuery.getSingleResult();
    }
}
