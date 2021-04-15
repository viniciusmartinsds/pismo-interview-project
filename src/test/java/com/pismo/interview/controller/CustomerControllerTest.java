package com.pismo.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.interview.domain.customer.CustomerService;
import com.pismo.interview.domain.customer.dto.AccountDto;
import com.pismo.interview.generated.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    void givenValidBody_whenCreateAccount_thenReturns201() throws Exception {
        var documentNumber = "123456789";
        var body = new AccountRequest();
        body.setDocumentNumber(documentNumber);

        mockMvc.perform(post("/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());

        verify(customerService).create(documentNumber);
    }

    @Test
    void givenBodyWithBlankPropertyDocumentNumber_whenCreateAccount_thenReturns400() throws Exception {
        var body = new AccountRequest();

        mockMvc.perform(post("/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNullBody_whenCreateAccount_thenReturns400() throws Exception {
        var body = new AccountRequest();

        mockMvc.perform(post("/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidPathParam_whenFindAccount_thenReturns200() throws Exception {
        var accountId = 1L;
        var dto = new AccountDto(accountId, "123456789");
        var accountResponse = new AccountResponse();
        accountResponse.setAccountId(dto.getAccountId());
        accountResponse.setDocumentNumber(dto.getDocumentNumber());

        when(customerService.find(accountId)).thenReturn(dto);

        var mvcResult = mockMvc.perform(get("/accounts/{accountId}", accountId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(accountResponse));
    }

    @Test
    void givenValidBody_whenCreateTransaction_thenReturns201() throws Exception {
        var accountId = 1L;
        var operationTypeId = 1L;
        var amount = 100.00;
        var transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(accountId);
        transactionRequest.setOperationTypeId(operationTypeId);
        transactionRequest.amount(BigDecimal.valueOf(amount));

        mockMvc.perform(post("/transactions")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isCreated());

        verify(customerService).createTransaction(transactionRequest);
    }

    @Test
    void givenInvalidBody_whenCreateTransaction_thenReturns404() throws Exception {
        var transactionRequest = new TransactionRequest();
        var errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Void> com.pismo.interview.controller.CustomerController.createTransaction(com.pismo.interview.generated.model.TransactionRequest) with 3 errors: [Field error in object 'transactionRequest' on field 'accountId': rejected value [null]; codes [NotNull.transactionRequest.accountId,NotNull.accountId,NotNull.java.lang.Long,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transactionRequest.accountId,accountId]; arguments []; default message [accountId]]; default message [must not be null]] [Field error in object 'transactionRequest' on field 'operationTypeId': rejected value [null]; codes [NotNull.transactionRequest.operationTypeId,NotNull.operationTypeId,NotNull.java.lang.Long,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transactionRequest.operationTypeId,operationTypeId]; arguments []; default message [operationTypeId]]; default message [must not be null]] [Field error in object 'transactionRequest' on field 'amount': rejected value [null]; codes [NotNull.transactionRequest.amount,NotNull.amount,NotNull.java.math.BigDecimal,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transactionRequest.amount,amount]; arguments []; default message [amount]]; default message [must not be null]] ");
        errorResponse.addValidationErrosItem(new ValidationError().field("accountId").message("must not be null"));
        errorResponse.addValidationErrosItem(new ValidationError().field("operationTypeId").message("must not be null"));
        errorResponse.addValidationErrosItem(new ValidationError().field("amount").message("must not be null"));

        mockMvc.perform(post("/transactions")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
