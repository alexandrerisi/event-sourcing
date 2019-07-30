package com.example.eventsourcing.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyDebitDTO {

    private double debitAmount;
    private String currency;
}
