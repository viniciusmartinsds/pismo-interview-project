package com.pismo.interview.infrastructure.commons.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName) {
        super(String.format("Entity %s not found.", entityName));
    }
}
