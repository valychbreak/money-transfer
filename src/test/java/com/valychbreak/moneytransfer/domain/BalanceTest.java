package com.valychbreak.moneytransfer.domain;

import com.valychbreak.moneytransfer.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BalanceTest {

    private static final BigDecimal MINUS_ONE = new BigDecimal("-1");

    @Test
    void shouldAddAmountToBalance() {
        Balance balance = Balance.of(new BigDecimal(100));
        balance.debit(BigDecimal.TEN);

        assertThat(balance.getAmount()).isEqualTo(new BigDecimal(110));
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

    @Test
    void shouldSubtractAmountFromBalance() {
        Balance balance = Balance.of(new BigDecimal(100));
        balance.credit(BigDecimal.TEN);

        assertThat(balance.getAmount()).isEqualTo(new BigDecimal(90));
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsLessThanAmount() {
        Balance balance = Balance.of(new BigDecimal(9));
        assertThrows(InsufficientBalanceException.class, () -> balance.credit(BigDecimal.TEN));
    }
}