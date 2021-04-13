package com.pismo.interview.infrastructure.account;

import com.pismo.interview.infrastructure.account.entity.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

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
}
