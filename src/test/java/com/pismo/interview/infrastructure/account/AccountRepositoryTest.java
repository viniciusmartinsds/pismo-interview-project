package com.pismo.interview.infrastructure.account;

import com.pismo.interview.infrastructure.account.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


public class AccountRepositoryTest extends PostgresqlRepositoryContainerTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void givenAccount_whenSave_shouldSaveAccount() {
        var account = new Account();
        account.setDocumentNumber("123456789");
        accountRepository.save(account);


        assertThat(accountRepository.findById(account.getId())).isPresent();
    }
}
