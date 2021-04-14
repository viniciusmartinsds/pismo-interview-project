package com.pismo.interview.controller;

import com.pismo.interview.domain.account.AccountService;
import com.pismo.interview.generated.controller.AccountApi;
import com.pismo.interview.generated.model.AccountRequest;
import com.pismo.interview.generated.model.AccountResponse;
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

    @Override
    public ResponseEntity<AccountResponse> find(Long accountId) {
        var accountDto = accountService.find(accountId);
        var accountResponse = new AccountResponse();
        accountResponse.setAccountId(accountDto.getAccountId());
        accountResponse.setDocumentNumber(accountDto.getDocumentNumber());

        return ResponseEntity.ok(accountResponse);
    }
}
