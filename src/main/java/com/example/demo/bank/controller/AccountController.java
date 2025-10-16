package com.example.demo.bank.controller;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.dto.AccountDTO;
import com.example.demo.bank.dto.TransactionRequestDTO;
import com.example.demo.bank.service.AccountService;
import com.example.demo.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{accountId}")
    public ResponseEntity<ResponseObject<AccountDTO>> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccount(accountId);
        return ResponseEntity.ok(ResponseObject.success(AccountDTO.fromAccount(account)));
    }

    @PostMapping("{accountId}/deposit")
    public ResponseEntity<ResponseObject<AccountDTO>> deposit(@PathVariable Long accountId,
                                                              @RequestBody TransactionRequestDTO transactionRequestDTO) {
        Account deposited = accountService.deposit(accountId, transactionRequestDTO.amount());
        return ResponseEntity.ok(ResponseObject.success(AccountDTO.fromAccount(deposited)));
    }

    @PostMapping("{accountId}/withdraw")
    public ResponseEntity<ResponseObject<AccountDTO>> withdraw(@PathVariable Long accountId,
                                                               @RequestBody TransactionRequestDTO transactionRequestDTO) {
        Account withdrawn = accountService.withdraw(accountId, transactionRequestDTO.amount());
        return ResponseEntity.ok(ResponseObject.success(AccountDTO.fromAccount(withdrawn)));
    }

}
