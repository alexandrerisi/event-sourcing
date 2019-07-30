package com.example.eventsourcing.domain.event;

import com.example.eventsourcing.domain.event.base.BaseEvent;
import lombok.Getter;

@Getter
public class MoneyCreditedEvent extends BaseEvent<String> {

    private final double creditAmount;
    private final String currency;

    public MoneyCreditedEvent(String id, double creditAmount, String currency) {
        super(id);
        this.creditAmount = creditAmount;
        this.currency = currency;
    }
}
