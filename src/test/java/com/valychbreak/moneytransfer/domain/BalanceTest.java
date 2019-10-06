package com.valychbreak.moneytransfer.domain;

import com.valychbreak.moneytransfer.InsufficientBalanceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BalanceTest {

    private static final BigDecimal MINUS_ONE = new BigDecimal("-1");

    @ParameterizedTest
    @ValueSource(doubles = {0.03, 10, 45.54, 1000})
    void shouldAddAmountToBalance(double amount) {
        BigDecimal initialAmount = new BigDecimal(100);
        Balance balance = Balance.of(initialAmount);

        BigDecimal debitAmount = new BigDecimal(amount);
        balance.debit(debitAmount);

        BigDecimal expectedBalance = initialAmount.add(debitAmount).setScale(2, RoundingMode.HALF_UP);
        assertThat(balance.getAmount()).isEqualTo(expectedBalance);
    }

    @Test
    void shouldThrowExceptionWhenDebitingNegativeAmount() {
        Balance balance = Balance.of(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> balance.debit(MINUS_ONE));
    }

    @Test
    void shouldThrowExceptionWhenCreditingNegativeAmount() {
        Balance balance = Balance.of(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> balance.credit(MINUS_ONE));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 1, 99})
    void shouldSubtractAmountFromBalance(double amount) {
        BigDecimal initialAmount = new BigDecimal(100);
        Balance balance = Balance.of(initialAmount);

        BigDecimal creditAmount = new BigDecimal(amount);
        balance.credit(creditAmount);

        BigDecimal expectedAmount = initialAmount.subtract(creditAmount).setScale(2, RoundingMode.HALF_UP);
        assertThat(balance.getAmount()).isEqualTo(expectedAmount);
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsLessThanAmount() {
        Balance balance = Balance.of(new BigDecimal(9));
        assertThrows(InsufficientBalanceException.class, () -> balance.credit(BigDecimal.TEN));
    }
}