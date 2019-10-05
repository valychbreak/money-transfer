package com.valychbreak.moneytransfer.domain;

import com.valychbreak.moneytransfer.InsufficientBalanceException;
import lombok.*;

import java.math.BigDecimal;

@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString
public class Balance {

    @Getter
    @NonNull
    private BigDecimal amount;

    public Balance() {
        this.amount = BigDecimal.ZERO;
    }

    public void debit(BigDecimal amount) {
        if (isNegative(amount)) {
            throw new IllegalArgumentException("Cannot debit negative value");
        }

        this.amount = this.amount.add(amount);
    }

    public void credit(BigDecimal amount) {
        if (isNegative(amount)) {
            throw new IllegalArgumentException("Cannot credit negative value");
        }

        if (this.amount.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Not enough balance. Having: " + this.amount + "; to credit: " + amount);
        }

        this.amount = this.amount.subtract(amount);
    }

    private boolean isNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }
}
