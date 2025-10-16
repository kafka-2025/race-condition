package com.example.demo.bank.dto;

import com.example.demo.bank.domain.Account;

import java.math.BigDecimal;

public record AccountDTO(Long id, String accountNumber, BigDecimal balance, UserDTO owner) {

    public static AccountDTO fromAccount(Account account) {
        UserDTO user = new UserDTO(account.getOwner().getName());
        return new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                user
        );
    }
}
