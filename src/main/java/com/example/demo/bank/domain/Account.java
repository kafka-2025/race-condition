package com.example.demo.bank.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String accountNumber;   // 계좌번호

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;     // 잔액

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public Account() {}

    public Account(String accountNumber, BigDecimal balance, User owner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
