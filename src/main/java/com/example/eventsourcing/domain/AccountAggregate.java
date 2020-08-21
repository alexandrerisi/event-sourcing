package com.example.eventsourcing.domain;

import com.example.eventsourcing.coreapi.*;
import com.example.eventsourcing.exception.InvalidDepositException;
import com.example.eventsourcing.exception.InvalidWithdrawException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

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
    public AccountAggregate(CreateAccountCmd createAccountCommand) {
        apply(new AccountCreatedEvt(createAccountCommand.getId(),
                createAccountCommand.getAccountBalance(),
                createAccountCommand.getCurrency()));
    }

    @CommandHandler
    protected void handle(CreditMoneyCmd creditMoneyCommand) {

        if (creditMoneyCommand.getCreditAmount() <= 0)
            throw new InvalidDepositException(id);

        apply(new MoneyCreditedEvt(creditMoneyCommand.getId(),
                creditMoneyCommand.getCreditAmount()));

    }

    @CommandHandler
    protected void handle(DebitMoneyCmd debitMoneyCommand) {

        if (debitMoneyCommand.getDebitAmount() <= 0)
            throw new InvalidWithdrawException(id);

        apply(new MoneyDebitedEvt(debitMoneyCommand.getId(),
                debitMoneyCommand.getDebitAmount()));
    }

    @EventSourcingHandler
    protected void handle(AccountCreatedEvt accountCreatedEvent) {
        id = accountCreatedEvent.getId();
        accountBalance = accountCreatedEvent.getAccountBalance();
        currency = accountCreatedEvent.getCurrency();
        status = AccountStatus.CREATED;

        apply(new AccountActivatedEvt(id));
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvt accountActivatedEvent) {
        status = AccountStatus.ACTIVATED;
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvt moneyCreditedEvt) {
        accountBalance += moneyCreditedEvt.getCreditedAmount();
        if (status != AccountStatus.ACTIVATED && accountBalance >= 0)
            apply(new AccountActivatedEvt(id));
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvt moneyDebitedEvent) {

        if (accountBalance >= 0 & (accountBalance - moneyDebitedEvent.getDebitedAmount()) < 0)
            apply(new AccountHeldEvt(id));

        accountBalance -= moneyDebitedEvent.getDebitedAmount();
    }

    @EventSourcingHandler
    protected void on(AccountHeldEvt accountHeldEvent) {
        status = AccountStatus.HOLD;
    }
}
