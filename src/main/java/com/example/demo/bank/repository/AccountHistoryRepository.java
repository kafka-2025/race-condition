package com.example.demo.bank.repository;

import com.example.demo.bank.domain.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
    List<AccountHistory> findAllByAccountId(Long accountId);
}
