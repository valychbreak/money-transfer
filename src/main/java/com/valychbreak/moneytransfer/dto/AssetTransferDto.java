package com.valychbreak.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@ToString
public class AssetTransferDto {
    private final String senderAccountNumber;
    private final String receiverAccountNumber;
    private final BigDecimal transferAmount;
}
