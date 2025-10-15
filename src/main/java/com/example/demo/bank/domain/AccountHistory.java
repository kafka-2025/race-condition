package com.example.demo.bank.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_account_history")
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

    public AccountHistory() {}

    public AccountHistory(Account account, TransType transType, BigDecimal amount) {
        this.account = account;
        this.transType = transType;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public TransType getTransType() {
        return transType;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
