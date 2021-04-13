package com.pismo.interview.domain.account;

import com.pismo.interview.infrastructure.account.AccountClient;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountClient accountClient;

    public AccountService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    public void create(String documentNumber) {
        accountClient.create(documentNumber);
    }
}
