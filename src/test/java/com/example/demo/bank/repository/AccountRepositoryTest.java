package com.example.demo.bank.repository;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Account account;

    @BeforeEach
    void setUp() {
        user = new User("홍길동");
        userRepository.save(user);
        account = new Account("12345", new BigDecimal("100.00"), user);
        accountRepository.save(account);
    }

    @Test
    void findById_정상계좌조회() {
        // given
        Long id = account.getId();

        // when
        Optional<Account> account = accountRepository.findById(id);

        // then
        assertThat(account).isPresent();
        assertThat(account.get().getAccountNumber()).isEqualTo("12345");
        assertThat(account.get().getBalance()).isEqualTo(new BigDecimal("100.00"));
        assertThat(account.get().getOwner().getName()).isEqualTo("홍길동");
    }


    @Test
    void save_정상입금() {
        // given
        BigDecimal deposit = new BigDecimal("50.00");
        Account account = this.account;

        // when
        account.setBalance(account.getBalance().add(deposit));
        accountRepository.save(account);

        // then
        Account updated = accountRepository.findByIdWithLock(account.getId()).orElseThrow();
        assertThat(updated.getBalance()).isEqualTo(new BigDecimal("150.00"));
    }

    @Test
    void save_정상출금() {
        // given
        BigDecimal withdraw = new BigDecimal("25.00");
        Account account = this.account;

        // when
        account.setBalance(account.getBalance().subtract(withdraw));
        accountRepository.save(account);

        // then
        Account updated = accountRepository.findByIdWithLock(account.getId()).orElseThrow();
        assertThat(updated.getBalance()).isEqualTo(new BigDecimal("75.00"));
    }
}