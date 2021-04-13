package com.pismo.interview.domain.account;

import com.pismo.interview.infrastructure.account.AccountClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountClient accountClient;

    @Test
    void givenDocumentNumber_whenCreateAccount_thenCallClientCreateAccountWithTheProperty() {
        var documentNumber = "123456789";

        accountService.create(documentNumber);

        verify(accountClient).create(documentNumber);
    }
}
