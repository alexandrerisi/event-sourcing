package com.example.eventsourcing.service;

import com.example.eventsourcing.coreapi.*;
import com.example.eventsourcing.domain.AccountAggregate;
import com.example.eventsourcing.domain.AccountEntity;
import com.example.eventsourcing.domain.AccountStatus;
import com.example.eventsourcing.exception.AccountNotFoundException;
import com.example.eventsourcing.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.ExecutionException;
import org.axonframework.modelling.command.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Profile("query")
public class AccountProjectionService {

    private final AccountRepository repository;
    private final Repository<AccountAggregate> aggregateRepository;

    @EventHandler
    public void on(AccountCreatedEvt evt) {
        var account = new AccountEntity(evt.getId(), evt.getAccountBalance(), evt.getCurrency(), AccountStatus.CREATED);
        repository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvt evt) {
        modifyAccountStatus(evt.getId(), AccountStatus.ACTIVATED);
    }

    @EventHandler
    public void on(AccountHeldEvt evt) {
        modifyAccountStatus(evt.getId(), AccountStatus.HOLD);
    }

    @EventHandler
    public void on(MoneyCreditedEvt evt) {
        modifyAccountBalance(evt.getId(), evt.getCreditedAmount());
    }

    @EventHandler
    public void on(MoneyDebitedEvt evt) {
        modifyAccountBalance(evt.getId(), - evt.getDebitedAmount());
    }

    @QueryHandler
    public AccountEntity handle(GetBankAccountQuery query) {
        return this.repository.findById(query.getId()).orElse(null);
    }

    @QueryHandler
    public CompletableFuture<AccountAggregate> handle(GetAccountAggregateQuery query) throws ExecutionException {
        var future = new CompletableFuture<AccountAggregate>();
        aggregateRepository.load(query.getId()).execute(future::complete);
        return future;
    }

    public void modifyAccountStatus(String id, AccountStatus status) {
        repository.findById(id).
                ifPresentOrElse(account -> {
                    account.setStatus(status);
                    repository.save(account);
                }, () -> {
                    throw new RuntimeException(id);
                });
    }

    public void modifyAccountBalance(String id, double amount) {
        repository.findById(id).
                ifPresentOrElse(account -> {
                    account.setAmount(account.getAmount() + amount);
                    repository.save(account);
                }, () -> {
                    throw new AccountNotFoundException(id);
                });
    }
}
