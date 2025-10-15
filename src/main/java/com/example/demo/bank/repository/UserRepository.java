package com.example.demo.bank.repository;

import com.example.demo.bank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
