package com.pismo.interview.infrastructure;

import com.pismo.interview.domain.customer.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class AccountRepositoryTest extends PostgresqlRepositoryContainerTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void givenAccount_whenSave_shouldSaveAccount() {
        var documentNumber = UUID.randomUUID().toString();
        var account = new Account();
        account.setDocumentNumber(documentNumber);
        accountRepository.save(account);


        assertThat(accountRepository.findById(account.getId())).isPresent();
    }

    @Test
    void givenExistentId_whenFind_shouldFindAccount() {
        var documentNumber = UUID.randomUUID().toString();
        var account = new Account();
        account.setDocumentNumber(documentNumber);

        account = accountRepository.save(account);

        var accountFound = accountRepository.findById(account.getId());

        assertThat(accountFound).isPresent();
        assertThat(accountFound.get().getId()).isEqualTo(account.getId());
        assertThat(accountFound.get().getDocumentNumber()).isEqualTo(account.getDocumentNumber());
    }
}
