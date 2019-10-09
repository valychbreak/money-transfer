package com.valychbreak.moneytransfer.service;

import com.valychbreak.moneytransfer.domain.Account;
import com.valychbreak.moneytransfer.domain.Balance;
import com.valychbreak.moneytransfer.exception.DataValidationException;
import com.valychbreak.moneytransfer.exception.InsufficientBalanceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static com.valychbreak.moneytransfer.service.AccountBuilder.aRandomAccount;
import static com.valychbreak.moneytransfer.service.AccountBuilder.anAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountAssetServiceTest {

    private AccountAssetService accountAssetService;

    @BeforeEach
    void setUp() {
        accountAssetService = new AccountAssetService();
    }

    @Test
    void shouldTransferAssetsFromOneAccountToAnother() throws DataValidationException {
        Account senderAccount = anAccount()
                .withRandomValidNumber()
                .withBalance(50)
                .build();

        Account receiverAccount = anAccount()
                .withRandomValidNumber()
                .withBalance(100)
                .build();

        accountAssetService.transfer(senderAccount, receiverAccount, BigDecimal.TEN);

        Balance expectedSenderBalance = Balance.of(new BigDecimal(40));
        Balance expectedReceiverBalance = Balance.of(new BigDecimal(110));

        assertThat(senderAccount.getBalance()).isEqualTo(expectedSenderBalance);
        assertThat(receiverAccount.getBalance()).isEqualTo(expectedReceiverBalance);
    }

    @Test
    void shouldThrowExceptionWhenAccountNumbersAreTheSame() {
        String accountNumber = RandomStringUtils.randomAlphabetic(26);
        Account sender = anAccount()
                .withNumber(accountNumber)
                .withBalance(100)
                .build();

        Account receiver = anAccount()
                .withNumber(accountNumber)
                .withBalance(45)
                .build();

        assertThrows(DataValidationException.class, () -> accountAssetService.transfer(sender, receiver, BigDecimal.ONE));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -0.01, -14325})
    void shouldThrowExceptionWhenAmountIsNotPositive(double amount) {
        Account sender = aRandomAccount().build();
        Account receiver = aRandomAccount().build();

        Exception thrownException = assertThrows(DataValidationException.class, () -> {
            accountAssetService.transfer(sender, receiver, new BigDecimal(amount));
        });

        assertThat(thrownException.getMessage()).isEqualTo("Amount must be positive");
    }

    @Test
    void shouldThrowExceptionWhenSenderBalanceIsLessThanAmount() {
        Account sender = aRandomAccount().withBalance(10).build();
        Account receiver = aRandomAccount().build();

        assertThrows(DataValidationException.class, () -> accountAssetService.transfer(sender, receiver, new BigDecimal(11)));
    }
}