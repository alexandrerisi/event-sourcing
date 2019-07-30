package com.example.eventsourcing.domain.event.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
public class BaseEvent<T> {

    @TargetAggregateIdentifier
    private final T id;
}
