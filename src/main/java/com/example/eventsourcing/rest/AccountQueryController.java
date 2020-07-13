package com.example.eventsourcing.rest;

import com.example.eventsourcing.domain.AccountEntity;
import com.example.eventsourcing.service.AccountQueryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/bank-accounts")
@Api(value = "Account Query Events Endpoint", tags = "Account Queries")
@RequiredArgsConstructor
public class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @GetMapping("/{accountNumber}/events")
    public List<Object> listEventsForAccount(@PathVariable String accountNumber){
        return accountQueryService.listEventsForAccount(accountNumber);
    }

    @GetMapping("/{accountNumber}")
    public CompletableFuture<AccountEntity> getAccountInfo(@PathVariable String accountNumber) {
        return accountQueryService.getAccount(accountNumber);
    }
}
