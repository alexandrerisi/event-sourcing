package com.example.eventsourcing.repository;

import com.example.eventsourcing.domain.AccountEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile("query")
public interface AccountRepository extends CrudRepository<AccountEntity, String> {
}
