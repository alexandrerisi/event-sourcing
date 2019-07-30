package com.example.eventsourcing.domain.event;

import com.example.eventsourcing.domain.event.base.BaseEvent;
import lombok.Getter;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {

    private final double accountBalance;
    private final String currency;

    public AccountCreatedEvent(String id, double accountBalance, String currency) {
        super(id);
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}
