package com.example.eventsourcing.domain.event;

import com.example.eventsourcing.domain.event.base.BaseEvent;
import lombok.Getter;

@Getter
public class MoneyDebitedEvent extends BaseEvent<String> {

    private final double debitAmount;
    private final String currency;

    public MoneyDebitedEvent(String id, double debitAmount, String currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }
}
