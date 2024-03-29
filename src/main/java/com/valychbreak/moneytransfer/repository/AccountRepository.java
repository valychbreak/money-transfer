package com.valychbreak.moneytransfer.repository;

import com.google.inject.persist.Transactional;
import com.valychbreak.moneytransfer.domain.Account;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

@Singleton
public class AccountRepository {

    private Provider<EntityManager> entityManagerProvider;

    @Inject
    AccountRepository(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public Account findByAccountNumber(String number) {
        TypedQuery<Account> accountByNumberQuery = getEntityManager()
                .createQuery("SELECT account From Account account where account.number = :number", Account.class)
                .setParameter("number", number)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE);

        return accountByNumberQuery.getSingleResult();
    }

    @Transactional
    public void create(Account account) {
        EntityManager entityManager = getEntityManager();
        entityManager.persist(account);
        entityManager.flush();
    }

    private EntityManager getEntityManager() {
        return entityManagerProvider.get();
    }
}
