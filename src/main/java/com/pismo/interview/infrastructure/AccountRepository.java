package com.pismo.interview.infrastructure;

import com.pismo.interview.domain.customer.models.Account;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Cacheable(value = "Account")
    Optional<Account> findById(Long id);
}
