package com.pismo.interview.application.handlers;

import com.pismo.interview.application.exceptions.EntityNotFoundException;
import com.pismo.interview.application.exceptions.TransactionNotAllowedException;
import com.pismo.interview.generated.model.ErrorResponse;
import com.pismo.interview.generated.model.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String CONSTRAINT = "constraint";
    private static final String DETAILS = "details";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        var errorResponse = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getLocalizedMessage());

        if (exception.getCause() instanceof ConstraintViolationException) {
            var constraintException = ((ConstraintViolationException) exception.getCause());

            errorResponse.setStatus(HttpStatus.CONFLICT.value());
            errorResponse.addValidationErrosItem(new ValidationError().field(CONSTRAINT).message(constraintException.getConstraintName()));
            errorResponse.addValidationErrosItem(new ValidationError().field(DETAILS).message(constraintException.getSQLException().getLocalizedMessage()));

            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());

        var result = exception.getBindingResult();
        var fields = result.getFieldErrors();
        fields.forEach(error -> errorResponse.addValidationErrosItem(new ValidationError().field(error.getField()).message(error.getDefaultMessage())));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        var errorResponse = createErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(TransactionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotAllowedException(TransactionNotAllowedException exception) {
        var errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private ErrorResponse createErrorResponse(int httpStatus, String message) {
        var errorResponse = new ErrorResponse();

        errorResponse.setStatus(httpStatus);
        errorResponse.setMessage(message);

        return errorResponse;
    }


}
