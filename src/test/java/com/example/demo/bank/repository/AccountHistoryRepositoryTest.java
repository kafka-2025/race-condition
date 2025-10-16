package com.example.demo.bank.repository;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.AccountHistory;
import com.example.demo.bank.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static com.example.demo.bank.domain.AccountHistory.TransType.DEPOSIT;
import static com.example.demo.bank.domain.AccountHistory.TransType.WITHDRAW;


@DataJpaTest
class AccountHistoryRepositoryTest {

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private User user;
    private Account account;
    private AccountHistory accountHistory;

    @BeforeEach
    void setUp() {
        user = new User("홍길동");
        userRepository.save(user);
        account = new Account("12345", new BigDecimal("0.00"), user);
        accountRepository.save(account);
        accountHistoryRepository.saveAll(List.of(
           new AccountHistory(account, DEPOSIT, new BigDecimal("100")),
           new AccountHistory(account, WITHDRAW, new BigDecimal("50")),
           new AccountHistory(account, DEPOSIT, new BigDecimal("110"))
        ));
    }

    @Test
    void findByAccount_입출금내역조회() {
        // given

        // when
        List<AccountHistory> allHistory = accountHistoryRepository.findAllByAccountId(account.getId());


        // then
        Assertions.assertThat(allHistory).hasSize(3);
        Assertions.assertThat(allHistory.get(0).getTransType()).isEqualTo(DEPOSIT);
        Assertions.assertThat(allHistory.get(1).getTransType()).isEqualTo(WITHDRAW);
        Assertions.assertThat(allHistory.get(2).getTransType()).isEqualTo(DEPOSIT);

    }
}