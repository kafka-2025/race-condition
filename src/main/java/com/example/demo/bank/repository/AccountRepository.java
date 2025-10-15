package com.example.demo.bank.repository;

import com.example.demo.bank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
