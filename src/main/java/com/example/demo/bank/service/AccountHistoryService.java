package com.example.demo.bank.service;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.AccountHistory;
import com.example.demo.bank.repository.AccountHistoryRepository;
import com.example.demo.bank.repository.AccountRepository;
import com.example.demo.common.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;
    private final AccountRepository accountRepository;

    public AccountHistoryService(AccountHistoryRepository accountHistoryRepository, AccountRepository accountRepository) {
        this.accountHistoryRepository = accountHistoryRepository;
        this.accountRepository = accountRepository;
    }

    public List<AccountHistory> getAccountHistories(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("계좌를 찾을 수 없습니다. id="+ accountId));
        return accountHistoryRepository.findAllByAccount(account);
    }
}
