package com.example.eventsourcing.service;

import com.example.eventsourcing.coreapi.GetBankAccountQuery;
import com.example.eventsourcing.domain.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountQueryService {

    private final EventStore eventStore;
    private final QueryGateway queryGateway;

    public List<Object> listEventsForAccount(String accountNumber) {
        return eventStore.readEvents(accountNumber)
                .asStream()
                .map(msg -> new AccountMessage(msg.getPayload(), msg.getTimestamp(), msg.getPayloadType().getName()))
                .collect(Collectors.toList());
    }

    public CompletableFuture<AccountEntity> getAccount(String id) {
        return queryGateway.query(new GetBankAccountQuery(id), AccountEntity.class);
    }

    @AllArgsConstructor
    @Data
    static class AccountMessage {
        Object message;
        Instant timestamp;
        String event;
    }
}
