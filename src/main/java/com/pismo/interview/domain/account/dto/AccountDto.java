package com.pismo.interview.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class AccountDto implements Serializable {
    private final Long accountId;
    private final String documentNumber;
}
