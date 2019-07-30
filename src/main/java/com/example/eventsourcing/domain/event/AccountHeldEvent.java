package com.example.eventsourcing.domain.event;

import com.example.eventsourcing.domain.Status;
import com.example.eventsourcing.domain.event.base.BaseEvent;
import lombok.Getter;

@Getter
public class AccountHeldEvent extends BaseEvent<String> {

    private final Status status;

    public AccountHeldEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
