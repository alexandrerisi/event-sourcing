package com.example.eventsourcing.domain.event;

import com.example.eventsourcing.domain.Status;
import com.example.eventsourcing.domain.event.base.BaseEvent;
import lombok.Getter;

@Getter
public class AccountActivatedEvent extends BaseEvent<String> {

    private final Status status;

    public AccountActivatedEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
