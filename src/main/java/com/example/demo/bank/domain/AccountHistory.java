package com.example.demo.bank.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class AccountHistory {

    public enum TransType {
        DEPOSIT, WITHDRAW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransType transType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
}
