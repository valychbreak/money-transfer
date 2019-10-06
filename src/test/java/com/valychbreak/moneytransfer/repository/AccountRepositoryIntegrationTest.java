package com.valychbreak.moneytransfer.repository;

import com.valychbreak.moneytransfer.HibernateUtil;
import com.valychbreak.moneytransfer.domain.Account;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valychbreak.moneytransfer.service.AccountBuilder.anAccount;
import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryIntegrationTest {

    private AccountRepository accountRepository;
    private Session session;

    @BeforeEach
    void setUp() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        accountRepository = new AccountRepository(session);
    }

    @AfterEach
    void tearDown() {
        session.getTransaction().commit();
        session.close();
        HibernateUtil.shutdown();
    }

    @Test
    void shouldFindAccountByNumber() {
        String accountNumber = RandomStringUtils.randomAlphabetic(26);
        Account account = anAccount()
                .withNumber(accountNumber)
                .withBalance(5000)
                .build();

        session.persist(account);
        session.flush();

        Account accountByNumber = accountRepository.findByAccountNumber(accountNumber);
        assertThat(accountByNumber).isEqualToComparingFieldByField(account);
    }
}