package com.example.eventsourcing.repository;

import com.example.eventsourcing.domain.AccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountEntity, String> {
}
