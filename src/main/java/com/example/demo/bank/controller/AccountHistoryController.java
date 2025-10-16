package com.example.demo.bank.controller;

import com.example.demo.bank.domain.AccountHistory;
import com.example.demo.bank.dto.AccountHistoryResponseDTO;
import com.example.demo.bank.service.AccountHistoryService;
import com.example.demo.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountHistoryController {

    private final AccountHistoryService accountHistoryService;

    public AccountHistoryController(AccountHistoryService accountHistoryService) {
        this.accountHistoryService = accountHistoryService;
    }

    @GetMapping("{accountId}/transactions")
    public ResponseEntity<ResponseObject<List<AccountHistoryResponseDTO>>> getAccountHistory(@PathVariable Long accountId) {
        List<AccountHistory> accountHistories = accountHistoryService.getAccountHistories(accountId);
        return ResponseEntity.ok(ResponseObject.success(AccountHistoryResponseDTO.fromAccountHistory(accountHistories)));
    }
}
