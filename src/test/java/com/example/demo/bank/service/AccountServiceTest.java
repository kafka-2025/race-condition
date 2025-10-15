package com.example.demo.bank.service;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.AccountHistory;
import com.example.demo.bank.domain.User;
import com.example.demo.bank.repository.AccountHistoryRepository;
import com.example.demo.bank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.bank.domain.AccountHistory.TransType.DEPOSIT;
import static com.example.demo.bank.domain.AccountHistory.TransType.WITHDRAW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountHistoryRepository accountHistoryRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void getAccount_정상조회() {
        // given
        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(new Account("12345",
                        new BigDecimal("100"),
                        new User("홍길동")))
                );

        // when
        Account account = accountService.getAccount(1L);

        // then
        assertThat(account).isNotNull();
        assertThat(account.getBalance()).isEqualTo("100");
        assertThat(account.getOwner().getName()).isEqualTo("홍길동");

        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void deposit_정상입금() {
        // given
        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(new Account("12345",
                        new BigDecimal("100"),
                        new User("홍길동")))
                );
        // when
        Account deposited = accountService.deposit(1L, new BigDecimal("50"));

        // then
        assertThat(deposited).isNotNull();
        assertThat(deposited.getBalance()).isEqualTo("150");

        verify(accountHistoryRepository, times(1))
                .save(argThat(accountHistory ->
                        accountHistory.getAmount().equals(new BigDecimal("50")) &&
                        accountHistory.getTransType().equals(DEPOSIT)
                ));

        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void withdraw_정상출금() {
        // given
        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(new Account("12345",
                        new BigDecimal("100"),
                        new User("홍길동")))
                );
        // when
        Account withdrawn = accountService.withdraw(1L, new BigDecimal("50"));

        // then
        assertThat(withdrawn).isNotNull();
        assertThat(withdrawn.getBalance()).isEqualTo("50");

        verify(accountHistoryRepository, times(1))
                .save(argThat(accountHistory ->
                        accountHistory.getAmount().equals(new BigDecimal("50")) &&
                                accountHistory.getTransType().equals(WITHDRAW)
                ));

        verify(accountRepository, times(1)).findById(1L);
    }

}