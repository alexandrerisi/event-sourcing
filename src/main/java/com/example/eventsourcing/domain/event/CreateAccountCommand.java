package com.example.eventsourcing.domain.event;

import com.example.eventsourcing.domain.event.base.BaseCommand;
import lombok.Getter;

@Getter
public class CreateAccountCommand extends BaseCommand<String> {

    private final double accountBalance;
    private final String currency;

    public CreateAccountCommand(String id, double accountBalance, String currency) {
        super(id);
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}
