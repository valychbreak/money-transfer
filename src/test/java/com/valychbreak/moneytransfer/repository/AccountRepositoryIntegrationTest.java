package com.valychbreak.moneytransfer.repository;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.valychbreak.moneytransfer.config.PersistenceModule;
import com.valychbreak.moneytransfer.config.RepositoryModule;
import com.valychbreak.moneytransfer.domain.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.valychbreak.moneytransfer.service.AccountBuilder.anAccount;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryIntegrationTest {

    private static Injector injector;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private Provider<EntityManager> entityManagerProvider;

    @BeforeAll
    static void beforeAll() {
        injector = Guice.createInjector(new PersistenceModule(), new RepositoryModule());
    }

    @BeforeEach
    void setUp() {
        injector.injectMembers(this);
        entityManagerProvider.get().getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        entityManagerProvider.get().getTransaction().commit();
    }

    @Test
    void shouldFindAccountByNumber() {
        String accountNumber = randomNumeric(26);
        Account account = anAccount()
                .withNumber(accountNumber)
                .withBalance(5000)
                .build();

        accountRepository.create(account);

        Account accountByNumber = accountRepository.findByAccountNumber(accountNumber);
        assertThat(accountByNumber).isEqualToComparingFieldByField(account);
    }
}