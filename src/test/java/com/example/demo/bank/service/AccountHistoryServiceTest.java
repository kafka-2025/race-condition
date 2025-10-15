package com.example.demo.bank.service;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.AccountHistory;
import com.example.demo.bank.domain.User;
import com.example.demo.bank.repository.AccountHistoryRepository;
import com.example.demo.bank.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.demo.bank.domain.AccountHistory.TransType.DEPOSIT;
import static com.example.demo.bank.domain.AccountHistory.TransType.WITHDRAW;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountHistoryServiceTest {

    @Mock
    private AccountHistoryRepository accountHistoryRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountHistoryService accountHistoryService;

    private Account account;
    private AccountHistory history1, history2;

    @BeforeEach
    void setUp() {
        account = new Account("12345", new BigDecimal("100"), new User("홍길동"));
        history1 = new AccountHistory(account, DEPOSIT, new BigDecimal("100"));
        history2 = new AccountHistory(account, WITHDRAW, new BigDecimal("50"));
    }

    @Test
    void getAccountHistories() {
        // given
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountHistoryRepository.findAllByAccount(account)).thenReturn(List.of(history1, history2));

        // when
        List<AccountHistory> accountHistories = accountHistoryService.getAccountHistories(accountId);

        // then
        Assertions.assertThat(accountHistories).hasSize(2);
        Assertions.assertThat(accountHistories).contains(history1, history2);

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountHistoryRepository, times(1)).findAllByAccount(account);
    }
}