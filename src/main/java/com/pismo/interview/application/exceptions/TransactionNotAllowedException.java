package com.pismo.interview.application.exceptions;

public class TransactionNotAllowedException extends RuntimeException {

    public TransactionNotAllowedException(String message) {
        super(message);
    }
}
