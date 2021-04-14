package com.pismo.interview.domain.account;

import com.pismo.interview.infrastructure.account.AccountClient;
import com.pismo.interview.infrastructure.account.entity.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void givenAccountId_whenFindAccount_thenReturnDtoAndValidateCallClientWithAccountIdProperty() {
        var accountId = 1L;
        var documentNumber = "123456789";
        var account = new Account();
        account.setId(accountId);
        account.setDocumentNumber(documentNumber);

        when(accountClient.find(accountId)).thenReturn(account);

        var resultDto = accountService.find(accountId);

        assertThat(resultDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("accountId", accountId)
                .hasFieldOrPropertyWithValue("documentNumber", documentNumber);
    }
}
