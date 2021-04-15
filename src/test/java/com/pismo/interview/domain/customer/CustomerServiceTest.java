package com.pismo.interview.domain.customer;

import com.pismo.interview.application.exceptions.EntityNotFoundException;
import com.pismo.interview.application.exceptions.TransactionNotAllowedException;
import com.pismo.interview.domain.customer.models.Account;
import com.pismo.interview.domain.customer.models.OperationType;
import com.pismo.interview.domain.customer.models.Transaction;
import com.pismo.interview.generated.model.TransactionRequest;
import com.pismo.interview.infrastructure.AccountRepository;
import com.pismo.interview.infrastructure.OperationTypeRepository;
import com.pismo.interview.infrastructure.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Captor
    private ArgumentCaptor<OperationType> operationTypeArgumentCaptor;

    @Captor
    private ArgumentCaptor<Transaction> transactionArgumentCaptor;

    @Test
    void givenDocumentNumber_whenCreateAccount_thenVerifyRepositoryCall() {
        var documentNumber = "123456789";

        customerService.create(documentNumber);

        verify(accountRepository).save(accountArgumentCaptor.capture());
    }

    @Test
    void givenDocumentNumber_whenCreateAccountWithExistentDocumentNumber_thenThrowsDataIntegrityViolationException() {
        var documentNumber = "123456789";

        when(accountRepository.save(any(Account.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThatThrownBy(() -> customerService.create(documentNumber))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void givenAccountId_whenFindAccount_thenReturnAccountDto() {
        var accountId = 1L;
        var documentNumber = "123456789";

        var account = new Account();
        account.setId(accountId);
        account.setDocumentNumber(documentNumber);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        var resultDto = customerService.find(accountId);

        assertThat(resultDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("accountId", accountId)
                .hasFieldOrPropertyWithValue("documentNumber", documentNumber);
    }

    @Test
    void givenAccountId_whenFindAccountAndAccountNotExists_thenThrowsEntityNotFoundException() {
        var accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.find(accountId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entity Account not found.");
    }

    @Test
    void givenTransactionWithWithdrawOperationTypeAndPositiveAmount_whenCreateTransaction_thenThrowsTransactionNotAllowedException() {
        var operationTypeId = 1L;
        var amount = BigDecimal.valueOf(100.00);
        var transactionRequest = new TransactionRequest();
        transactionRequest.setOperationTypeId(operationTypeId);
        transactionRequest.setAmount(amount);
        var operationType = new OperationType();
        operationType.setDescription("SAQUE");

        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(operationType));

        assertThatThrownBy(() -> customerService.createTransaction(transactionRequest))
                .isInstanceOf(TransactionNotAllowedException.class)
                .hasMessage("Amount cannot be positive for withdraw or purchase transaction types.");
    }

    @Test
    void givenTransactionWitPurchaseOperationTypeAndPositiveAmount_whenCreateTransaction_thenThrowsTransactionNotAllowedException() {
        var operationTypeId = 1L;
        var amount = BigDecimal.valueOf(100.00);
        var transactionRequest = new TransactionRequest();
        transactionRequest.setOperationTypeId(operationTypeId);
        transactionRequest.setAmount(amount);
        var operationType = new OperationType();
        operationType.setDescription("COMPRA_A_VISTA");

        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(operationType));

        assertThatThrownBy(() -> customerService.createTransaction(transactionRequest))
                .isInstanceOf(TransactionNotAllowedException.class)
                .hasMessage("Amount cannot be positive for withdraw or purchase transaction types.");

        operationType.setDescription("COMPRA_PARCELADA");

        assertThatThrownBy(() -> customerService.createTransaction(transactionRequest))
                .isInstanceOf(TransactionNotAllowedException.class)
                .hasMessage("Amount cannot be positive for withdraw or purchase transaction types.");
    }

    @Test
    void givenTransactionWithPaymentOperationTypeAndNegativeAmount_whenCreateTransaction_thenThrowsTransactionNotAllowedException() {
        var operationTypeId = 1L;
        var amount = BigDecimal.valueOf(-100.00);
        var transactionRequest = new TransactionRequest();
        transactionRequest.setOperationTypeId(operationTypeId);
        transactionRequest.setAmount(amount);
        var operationType = new OperationType();
        operationType.setDescription("PAGAMENTO");

        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(operationType));

        assertThatThrownBy(() -> customerService.createTransaction(transactionRequest))
                .isInstanceOf(TransactionNotAllowedException.class)
                .hasMessage("Amount cannot be negative for payment transaction type.");
    }

    @Test
    void givenTransactionWithNotAllowedOperationType_whenCreateTransaction_thenThrowsTransactionNotAllowedException() {
        var operationTypeId = 1L;
        var transactionRequest = new TransactionRequest();
        transactionRequest.setOperationTypeId(operationTypeId);
        var operationType = new OperationType();
        operationType.setDescription("NOT_ALLOWD_OPERATION_TYPE");

        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(operationType));

        assertThatThrownBy(() -> customerService.createTransaction(transactionRequest))
                .isInstanceOf(TransactionNotAllowedException.class)
                .hasMessage("Operation type not allowed.");
    }
}
