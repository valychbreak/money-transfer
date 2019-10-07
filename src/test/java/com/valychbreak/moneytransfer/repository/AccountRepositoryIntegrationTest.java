package com.valychbreak.moneytransfer.repository;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.UnitOfWork;
import com.valychbreak.moneytransfer.config.PersistenceModule;
import com.valychbreak.moneytransfer.domain.Account;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valychbreak.moneytransfer.service.AccountBuilder.anAccount;
import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryIntegrationTest {

    private static Injector injector;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private UnitOfWork unitOfWork;

    @BeforeAll
    static void beforeAll() {
        injector = Guice.createInjector(new PersistenceModule(), new RepositoryModule());
    }

    @BeforeEach
    void setUp() {
        injector.injectMembers(this);
        assertThat(unitOfWork).isNotNull();

        unitOfWork.begin();
    }

    @AfterEach
    void tearDown() {
        unitOfWork.end();
    }

    @Test
    void shouldFindAccountByNumber() {
        String accountNumber = RandomStringUtils.randomAlphabetic(26);
        Account account = anAccount()
                .withNumber(accountNumber)
                .withBalance(5000)
                .build();

        accountRepository.create(account);

        Account accountByNumber = accountRepository.findByAccountNumber(accountNumber);
        assertThat(accountByNumber).isEqualToComparingFieldByField(account);
    }
}