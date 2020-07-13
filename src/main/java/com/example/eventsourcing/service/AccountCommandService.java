package com.example.eventsourcing.service;

import com.example.eventsourcing.coreapi.*;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AccountCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<?> createAccount(AccountCreateDTO accountCreateDTO) {
        return commandGateway.send(new CreateAccountCmd(UUID.randomUUID().toString(),
                accountCreateDTO.getStartingBalance(),
                accountCreateDTO.getCurrency()));
    }

    public CompletableFuture<?> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO) {
        return commandGateway.send(new CreditMoneyCmd(accountNumber, moneyCreditDTO.getCreditAmount()));
    }

    public CompletableFuture<?> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO) {
        return commandGateway.send(new DebitMoneyCmd(accountNumber, moneyDebitDTO.getDebitAmount()));
    }
}
