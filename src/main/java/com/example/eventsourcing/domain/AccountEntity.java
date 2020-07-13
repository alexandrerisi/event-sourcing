package com.example.eventsourcing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountEntity {

    @Id
    private String uuid;
    private double amount;
    private AccountCurrency currency;
    private AccountStatus status;
}
