package com.pismo.interview.domain.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDto {
    private final Long accountId;
    private final String documentNumber;
}
