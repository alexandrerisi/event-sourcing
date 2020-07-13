package com.example.eventsourcing.rest;

import com.example.eventsourcing.coreapi.AccountCreateDTO;
import com.example.eventsourcing.coreapi.MoneyCreditDTO;
import com.example.eventsourcing.coreapi.MoneyDebitDTO;
import com.example.eventsourcing.service.AccountCommandService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/bank-accounts")
@Api(value = "Account Commands Related Endpoints", tags = "Account Commands")
@RequiredArgsConstructor
public class AccountCommandController {

    private final AccountCommandService accountCommandService;

    @PostMapping
    public CompletableFuture<?> createAccount(@RequestBody AccountCreateDTO accountCreateDTO){
        return accountCommandService.createAccount(accountCreateDTO);
    }

    @PutMapping(value = "/credits/{accountNumber}")
    public CompletableFuture<?> creditMoneyToAccount(@PathVariable String accountNumber,
                                                          @RequestBody MoneyCreditDTO moneyCreditDTO){
        return accountCommandService.creditMoneyToAccount(accountNumber, moneyCreditDTO);
    }

    @PutMapping(value = "/debits/{accountNumber}")
    public CompletableFuture<?> debitMoneyFromAccount(@PathVariable String accountNumber,
                                                           @RequestBody MoneyDebitDTO moneyDebitDTO){
        return accountCommandService.debitMoneyFromAccount(accountNumber, moneyDebitDTO);
    }
}
