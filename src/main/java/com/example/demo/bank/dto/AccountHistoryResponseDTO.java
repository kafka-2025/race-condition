package com.example.demo.bank.dto;

import com.example.demo.bank.domain.AccountHistory;
import com.example.demo.bank.domain.AccountHistory.TransType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record AccountHistoryResponseDTO(Long id, TransType transType, BigDecimal amount) {
    public static List<AccountHistoryResponseDTO> fromAccountHistory(List<AccountHistory> accountHistories) {
        List<AccountHistoryResponseDTO> responseDtos = new ArrayList<>();
        for(AccountHistory accountHistory : accountHistories) {
            responseDtos.add(new AccountHistoryResponseDTO(
                    accountHistory.getId(),
                    accountHistory.getTransType(),
                    accountHistory.getAmount()
            ));
        }
        return responseDtos;
    }
}
