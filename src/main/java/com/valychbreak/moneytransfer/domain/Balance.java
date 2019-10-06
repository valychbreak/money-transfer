package com.valychbreak.moneytransfer.domain;

import com.valychbreak.moneytransfer.InsufficientBalanceException;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

        this.amount = normalize(this.amount.add(amount));
    }

    public void credit(BigDecimal amount) {
        if (isNegative(amount)) {
            throw new IllegalArgumentException("Cannot credit negative value");
        }

        if (this.amount.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Not enough balance. Having: " + this.amount + "; to credit: " + amount);
        }

        this.amount = normalize(this.amount.subtract(amount));
    }

    private boolean isNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    private BigDecimal normalize(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }
}
