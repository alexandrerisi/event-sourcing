package com.example.eventsourcing.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateDTO {

    private double startingBalance;
    private String currency;
}
