package com.example.eventsourcing.service;

import com.example.eventsourcing.domain.AccountCreateDTO;
import com.example.eventsourcing.domain.MoneyCreditDTO;
import com.example.eventsourcing.domain.MoneyDebitDTO;
import com.example.eventsourcing.domain.event.CreateAccountCommand;
import com.example.eventsourcing.domain.event.CreditMoneyCommand;
import com.example.eventsourcing.domain.event.DebitMoneyCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandService {

    @Autowired
    private CommandGateway commandGateway;

    public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO) {
        return commandGateway.send(new CreateAccountCommand(UUID.randomUUID().toString(),
                accountCreateDTO.getStartingBalance(),
                accountCreateDTO.getCurrency()));
    }

    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO) {
        return commandGateway.send(new CreditMoneyCommand(accountNumber, moneyCreditDTO.getCreditAmount(),
                moneyCreditDTO.getCurrency()));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO) {
        return commandGateway.send(new DebitMoneyCommand(accountNumber,
                moneyDebitDTO.getDebitAmount(),
                moneyDebitDTO.getCurrency()));
    }
}
