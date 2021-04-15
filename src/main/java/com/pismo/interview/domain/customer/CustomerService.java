package com.pismo.interview.domain.customer;

import com.pismo.interview.application.exceptions.EntityNotFoundException;
import com.pismo.interview.application.exceptions.TransactionNotAllowedException;
import com.pismo.interview.domain.customer.dto.AccountDto;
import com.pismo.interview.domain.customer.models.Account;
import com.pismo.interview.domain.customer.models.OperationType;
import com.pismo.interview.domain.customer.models.Transaction;
import com.pismo.interview.generated.model.TransactionRequest;
import com.pismo.interview.infrastructure.AccountRepository;
import com.pismo.interview.infrastructure.OperationTypeRepository;
import com.pismo.interview.infrastructure.TransactionRepository;
import com.pismo.interview.utils.BigDecimalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@Service
public class CustomerService {

    private static final String SAVE_ACCOUNT_MSG_ERROR = "Error when save new account with documentNumber: %s";

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final OperationTypeRepository operationTypeRepository;

    public CustomerService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            OperationTypeRepository operationTypeRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.operationTypeRepository = operationTypeRepository;
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

    public AccountDto find(Long accountId) {
        var account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException(Account.class.getSimpleName()));

        return new AccountDto(account.getId(), account.getDocumentNumber());
    }

    public void createTransaction(TransactionRequest transactionRequest) {
        var operationType = findOperationType(transactionRequest.getOperationTypeId());

        validateTransaction(operationType, transactionRequest.getAmount());

        var account = findAccount(transactionRequest.getAccountId());

        var transaction = new Transaction(account, operationType, transactionRequest.getAmount(), Instant.now());
        transactionRepository.save(transaction);
    }

    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Account.class.getSimpleName()));
    }

    private OperationType findOperationType(Long id) {
        return operationTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(OperationType.class.getSimpleName()));
    }

    public static void validateTransaction(OperationType operationType, BigDecimal amount) {

        if (operationType.isNotAllowed()) {
            throw new TransactionNotAllowedException("Operation type not allowed.");
        }

        if (operationType.isWithdrawOrPurchase() && BigDecimalUtils.isPositive(amount)) {
            throw new TransactionNotAllowedException("Amount cannot be positive for withdraw or purchase transaction types.");
        }

        if (operationType.isPayment() && BigDecimalUtils.isNegative(amount)) {
            throw new TransactionNotAllowedException("Amount cannot be negative for payment transaction type.");
        }
    }
}
