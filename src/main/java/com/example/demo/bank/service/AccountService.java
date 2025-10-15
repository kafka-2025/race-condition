package com.example.demo.bank.service;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.AccountHistory;
import com.example.demo.bank.repository.AccountHistoryRepository;
import com.example.demo.bank.repository.AccountRepository;
import com.example.demo.common.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.example.demo.bank.domain.AccountHistory.TransType.DEPOSIT;
import static com.example.demo.bank.domain.AccountHistory.TransType.WITHDRAW;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    public AccountService(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
    }

    @Transactional(readOnly = true)
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("계좌를 찾을 수 없습니다. id="+ accountId));
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance().add(amount));
        accountHistoryRepository.save(new AccountHistory(account, DEPOSIT, amount));
        return account;
    }

    @Transactional
    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance().subtract(amount));
        accountHistoryRepository.save(new AccountHistory(account, WITHDRAW, amount));
        return account;
    }
}
