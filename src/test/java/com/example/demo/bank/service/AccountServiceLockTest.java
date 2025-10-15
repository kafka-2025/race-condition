package com.example.demo.bank.service;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.User;
import com.example.demo.bank.repository.AccountHistoryRepository;
import com.example.demo.bank.repository.AccountRepository;
import com.example.demo.bank.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@SpringBootTest
class AccountServiceLockTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    private User user;
    private Account account;

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("DB Product Name: " + meta.getDatabaseProductName());
            System.out.println("DB Product Version: " + meta.getDatabaseProductVersion());
            System.out.println("DB URL: " + meta.getURL());
        }

        user = new User("홍길동3");
        userRepository.save(user);

        account = new Account("1234567", new BigDecimal("100.00"), user);
        accountRepository.save(account);
    }

    @Test
    void concurrentDepositServiceLock() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        BigDecimal depositAmount = new BigDecimal("10");

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    accountService.deposit(account.getId(), depositAmount);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        Account updated = accountRepository.findById(account.getId()).orElseThrow();
        Assertions.assertThat(updated.getBalance()).isEqualByComparingTo(new BigDecimal("1100.00"));

        // AccountHistory 검증
        Assertions.assertThat(accountHistoryRepository.findAllByAccount(account)).hasSize(threadCount);
    }


}