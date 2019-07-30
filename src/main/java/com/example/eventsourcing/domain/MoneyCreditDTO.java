package com.example.eventsourcing.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyCreditDTO {

    private double creditAmount;
    private String currency;
}
