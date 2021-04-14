package com.pismo.interview.domain.account;

import com.pismo.interview.domain.account.dto.AccountDto;
import com.pismo.interview.infrastructure.account.AccountClient;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "Account")
    public AccountDto find(Long accountId) {
        var account = accountClient.find(accountId);

        return new AccountDto(account.getId(), account.getDocumentNumber());
    }
}
