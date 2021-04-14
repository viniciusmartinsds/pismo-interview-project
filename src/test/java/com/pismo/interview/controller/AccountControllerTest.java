package com.pismo.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.interview.domain.account.AccountService;
import com.pismo.interview.domain.account.dto.AccountDto;
import com.pismo.interview.generated.model.AccountRequest;
import com.pismo.interview.generated.model.AccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    void givenValidBody_whenCreateAccount_thenReturns201() throws Exception {
        var body = new AccountRequest();
        body.setDocumentNumber("123456789");

        mockMvc.perform(post("/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
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

        when(accountService.find(accountId)).thenReturn(dto);

        MvcResult mvcResult = mockMvc.perform(get("/accounts/{accountId}", accountId)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();


    }
}
