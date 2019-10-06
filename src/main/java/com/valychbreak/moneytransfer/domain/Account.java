package com.valychbreak.moneytransfer.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    public static final int ACCOUNT_NUMBER_LENGTH = 26;

    @Id
    @Column(length = ACCOUNT_NUMBER_LENGTH)
    @EqualsAndHashCode.Include
    private String number;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance", nullable = false))
    })
    private Balance balance;
}
