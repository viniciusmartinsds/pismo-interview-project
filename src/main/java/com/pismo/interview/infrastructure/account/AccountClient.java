package com.pismo.interview.infrastructure.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountClient {

    private static final String SAVE_ACCOUNT_MSG_ERROR = "Error when save new account with documentNumber: %s";

    private final AccountRepository accountRepository;

    public AccountClient(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void create(String documentNumber) {
        var account = new Account();
        account.setDocumentNumber(documentNumber);

        try {
            accountRepository.save(account);
        } catch (DataIntegrityViolationException exception) {
            log.error(String.format(SAVE_ACCOUNT_MSG_ERROR, documentNumber), exception);

            throw exception;
        }
    }
}
