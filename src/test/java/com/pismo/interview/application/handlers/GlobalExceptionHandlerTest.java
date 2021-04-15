package com.pismo.interview.application.handlers;

import com.pismo.interview.application.exceptions.EntityNotFoundException;
import com.pismo.interview.application.exceptions.TransactionNotAllowedException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void givenExceptionOfTypeConstraintViolationException_whenHandleDataIntegrityViolationException_thenReturns409() {
        var exceptionMessage = "could not execute statement; SQL [n/a]; constraint [accounts_document_number_key]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
        var statusCode = HttpStatus.CONFLICT.value();
        var constraint = "accounts_document_number_key";
        var details = "ERROR: duplicate key value violates unique constraint \\\"accounts_document_number_key\\\"\\n  Detail: Key (document_number)=(1) already exists.";
        var sqlException = new SQLException(details);
        var causeException = new ConstraintViolationException(null, sqlException, constraint);
        var exception = new DataIntegrityViolationException(exceptionMessage, causeException);
        var expectedMsg = "could not execute statement; SQL [n/a]; constraint [accounts_document_number_key]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement; nested exception is org.hibernate.exception.ConstraintViolationException";

        var errorResponse = globalExceptionHandler.handleDataIntegrityViolationException(exception);

        assertThat(errorResponse.getBody()).isNotNull();
        assertThat(errorResponse.getBody().getStatus()).isEqualTo(statusCode);
        assertThat(errorResponse.getBody().getMessage()).isEqualTo(expectedMsg);
        assertThat(errorResponse.getBody().getValidationErros().get(0).getField()).isEqualTo("constraint");
        assertThat(errorResponse.getBody().getValidationErros().get(0).getMessage()).isEqualTo(constraint);
        assertThat(errorResponse.getBody().getValidationErros().get(1).getField()).isEqualTo("details");
        assertThat(errorResponse.getBody().getValidationErros().get(1).getMessage()).isEqualTo(details);
    }

    @Test
    void givenException_whenHandleDataIntegrityViolationException_thenReturns500() {
        var exceptionMessage = "could not execute statement; SQL [n/a]; constraint [accounts_document_number_key]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
        var statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        var exception = new DataIntegrityViolationException(exceptionMessage);

        var errorResponse = globalExceptionHandler.handleDataIntegrityViolationException(exception);

        assertThat(errorResponse.getBody()).isNotNull();
        assertThat(errorResponse.getBody().getStatus()).isEqualTo(statusCode);
        assertThat(errorResponse.getBody().getMessage()).isEqualTo(exceptionMessage);
    }

    @Test
    void givenException_whenHandleEntityNotFoundException_thenReturns404() {
        var exceptionMessage = "Entity Account not found.";
        var entity = "Account";
        var statusCode = HttpStatus.NOT_FOUND.value();
        var exception = new EntityNotFoundException(entity);

        var errorResponse = globalExceptionHandler.handleEntityNotFoundException(exception);

        assertThat(errorResponse.getBody()).isNotNull();
        assertThat(errorResponse.getBody().getStatus()).isEqualTo(statusCode);
        assertThat(errorResponse.getBody().getMessage()).isEqualTo(exceptionMessage);
    }

    @Test
    void givenException_whenHandleTransactionNotAllowedException_thenReturns400() {
        var exceptionMessage = "Operation type not allowed.";
        var statusCode = HttpStatus.BAD_REQUEST.value();
        var exception = new TransactionNotAllowedException(exceptionMessage);

        var errorResponse = globalExceptionHandler.handleTransactionNotAllowedException(exception);

        assertThat(errorResponse.getBody()).isNotNull();
        assertThat(errorResponse.getBody().getStatus()).isEqualTo(statusCode);
        assertThat(errorResponse.getBody().getMessage()).isEqualTo(exceptionMessage);
    }
}
