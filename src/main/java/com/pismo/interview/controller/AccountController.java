package com.pismo.interview.controller;

import com.pismo.interview.domain.account.AccountService;
import com.pismo.interview.generated.controller.AccountApi;
import com.pismo.interview.generated.model.AccountRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountApi {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<Void> create(AccountRequest body) {
        accountService.create(body.getDocumentNumber());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
