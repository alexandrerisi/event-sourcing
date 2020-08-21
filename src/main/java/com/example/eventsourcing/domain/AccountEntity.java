package com.example.eventsourcing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Profile("query")
public class AccountEntity {

    @Id
    private String uuid;
    private double amount;
    private AccountCurrency currency;
    private AccountStatus status;
}
