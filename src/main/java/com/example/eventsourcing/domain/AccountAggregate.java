package com.example.eventsourcing.domain;

import com.example.eventsourcing.coreapi.*;
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
    private AccountCurrency currency;
    private AccountStatus status;

    @CommandHandler
    public AccountAggregate(CreateAccountCmd createAccountCommand){
        AggregateLifecycle.apply(new AccountCreatedEvt(createAccountCommand.getId(),
                createAccountCommand.getAccountBalance(),
                createAccountCommand.getCurrency()));
    }

    @CommandHandler
    protected void handle(CreditMoneyCmd creditMoneyCommand){
        AggregateLifecycle.apply(new MoneyCreditedEvt(creditMoneyCommand.getId(),
                creditMoneyCommand.getCreditAmount()));
    }

    @CommandHandler
    protected void handle(DebitMoneyCmd debitMoneyCommand){
        AggregateLifecycle.apply(new MoneyDebitedEvt(debitMoneyCommand.getId(),
                debitMoneyCommand.getDebitAmount()));
    }

    @EventSourcingHandler
    protected void handle(AccountCreatedEvt accountCreatedEvent){
        this.id = accountCreatedEvent.getId();
        this.accountBalance = accountCreatedEvent.getAccountBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.status = AccountStatus.CREATED;

        AggregateLifecycle.apply(new AccountActivatedEvt(id));
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvt accountActivatedEvent){
        this.status = AccountStatus.ACTIVATED;
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvt moneyCreditedEvt){

        if (accountBalance < 0 & (accountBalance + moneyCreditedEvt.getCreditedAmount()) >= 0)
            AggregateLifecycle.apply(new AccountActivatedEvt(id));

        accountBalance += moneyCreditedEvt.getCreditedAmount();
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvt moneyDebitedEvent){

        if (accountBalance >= 0 & (accountBalance - moneyDebitedEvent.getDebitedAmount()) < 0)
            AggregateLifecycle.apply(new AccountHeldEvt(id));

        accountBalance -= moneyDebitedEvent.getDebitedAmount();
    }

    @EventSourcingHandler
    protected void on(AccountHeldEvt accountHeldEvent){
        status = AccountStatus.HOLD;
    }
}
