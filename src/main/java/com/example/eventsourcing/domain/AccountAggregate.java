package com.example.eventsourcing.domain;

import com.example.eventsourcing.domain.event.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@NoArgsConstructor
@Getter
@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String id;
    private double accountBalance;
    private String currency, status;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand){
        AggregateLifecycle.apply(new AccountCreatedEvent(createAccountCommand.getId(),
                createAccountCommand.getAccountBalance(),
                createAccountCommand.getCurrency()));
    }

    @CommandHandler
    protected void on(CreditMoneyCommand creditMoneyCommand){
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.getId(),
                creditMoneyCommand.getCreditAmount(),
                creditMoneyCommand.getCurrency()));
    }

    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand){
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.getId(),
                debitMoneyCommand.getDebitAmount(),
                debitMoneyCommand.getCurrency()));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent){
        this.id = accountCreatedEvent.getId();
        this.accountBalance = accountCreatedEvent.getAccountBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.status = String.valueOf(Status.CREATED);

        AggregateLifecycle.apply(new AccountActivatedEvent(id, Status.ACTIVATED));
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvent accountActivatedEvent){
        this.status = String.valueOf(accountActivatedEvent.getStatus());
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent){

        if (accountBalance < 0 & (accountBalance + moneyCreditedEvent.getCreditAmount()) >= 0)
            AggregateLifecycle.apply(new AccountActivatedEvent(id, Status.ACTIVATED));

        accountBalance += moneyCreditedEvent.getCreditAmount();
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent moneyDebitedEvent){

        if (accountBalance >= 0 & (accountBalance - moneyDebitedEvent.getDebitAmount()) < 0)
            AggregateLifecycle.apply(new AccountHeldEvent(id, Status.HOLD));

        accountBalance -= moneyDebitedEvent.getDebitAmount();
    }

    @EventSourcingHandler
    protected void on(AccountHeldEvent accountHeldEvent){
        status = String.valueOf(accountHeldEvent.getStatus());
    }
}
