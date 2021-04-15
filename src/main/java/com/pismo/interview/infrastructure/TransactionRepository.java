package com.pismo.interview.infrastructure;

import com.pismo.interview.domain.customer.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
