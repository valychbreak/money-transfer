package com.valychbreak.moneytransfer.repository;

import com.valychbreak.moneytransfer.domain.Account;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.valychbreak.moneytransfer.service.AccountBuilder.anAccount;
import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryIntegrationTest {

    private AccountRepository accountRepository;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("db-manager");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        accountRepository = new AccountRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void shouldFindAccountByNumber() {
        String accountNumber = RandomStringUtils.randomAlphabetic(26);
        Account account = anAccount()
                .withNumber(accountNumber)
                .withBalance(5000)
                .build();

        entityManager.persist(account);
        entityManager.flush();

        Account accountByNumber = accountRepository.findByAccountNumber(accountNumber);
        assertThat(accountByNumber).isEqualToComparingFieldByField(account);
    }
}