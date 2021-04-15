package com.pismo.interview.controller;

import com.pismo.interview.domain.customer.CustomerService;
import com.pismo.interview.generated.controller.CustomerApi;
import com.pismo.interview.generated.model.AccountRequest;
import com.pismo.interview.generated.model.AccountResponse;
import com.pismo.interview.generated.model.TransactionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<Void> create(AccountRequest body) {
        customerService.create(body.getDocumentNumber());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<AccountResponse> find(Long accountId) {
        var accountDto = customerService.find(accountId);
        var accountResponse = new AccountResponse();
        accountResponse.setAccountId(accountDto.getAccountId());
        accountResponse.setDocumentNumber(accountDto.getDocumentNumber());

        return ResponseEntity.ok(accountResponse);
    }

    @Override
    public ResponseEntity<Void> createTransaction(TransactionRequest body) {
        customerService.createTransaction(body);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
