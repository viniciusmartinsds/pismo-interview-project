package com.pismo.interview.infrastructure.account;

import com.pismo.interview.infrastructure.account.entity.Account;
import com.pismo.interview.infrastructure.commons.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountClientTest {

    @InjectMocks
    private AccountClient accountClient;

    @Mock
    private AccountRepository accountRepository;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Test
    void givenDocumentNumber_whenCreateAccount_thenVerifyParameterWhenCallRepository() {
        var documentNumber = "123456789";

        accountClient.create(documentNumber);

        verify(accountRepository).save(accountArgumentCaptor.capture());

        assertThat(accountArgumentCaptor.getValue().getDocumentNumber()).isEqualTo(documentNumber);
    }

    @Test
    void givenValidAccountId_whenFindAccount_thenReturnAccount() {
        var accountId = 1L;
        var account = new Account();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        var accountResult = accountClient.find(accountId);

        assertThat(accountResult).isEqualTo(account);
    }

    @Test
    void givenInvalidAccountId_whenFindAccount_thenThrowsEntityNotFoundException() {
        var accountId = 2L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountClient.find(accountId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entity Account not found.");
    }
}
