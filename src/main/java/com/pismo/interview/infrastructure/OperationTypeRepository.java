package com.pismo.interview.infrastructure;

import com.pismo.interview.domain.customer.models.OperationType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OperationTypeRepository extends CrudRepository<OperationType, Long> {

    @Cacheable(value = "OperationType")
    Optional<OperationType> findById(Long id);
}
