package com.example.eventsourcing.domain.event;

import com.example.eventsourcing.domain.event.base.BaseCommand;
import lombok.Getter;

@Getter
public class DebitMoneyCommand extends BaseCommand<String> {

    private final double debitAmount;
    private final String currency;

    public DebitMoneyCommand(String id, double debitAmount, String currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }
}
