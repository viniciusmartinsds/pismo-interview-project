package com.pismo.interview.controller;

import com.pismo.interview.api.AccountApi;
import com.pismo.interview.models.AccountRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountController implements AccountApi {

    @Override
    public ResponseEntity<Void> create(@Valid @RequestBody AccountRequest body) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
