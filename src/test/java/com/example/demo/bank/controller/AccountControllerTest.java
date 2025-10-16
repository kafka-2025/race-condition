package com.example.demo.bank.controller;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.User;
import com.example.demo.bank.dto.TransactionRequestDTO;
import com.example.demo.bank.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;    // HTTP 요청 시뮬레이터

    @MockitoBean    // Spring context에 Mock 객체 등록 + DI 주입
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAccount_정상조회() throws Exception {
        // given
        Long accountId = 1L;

        // Mock 동작 정의 (stub)
        when(accountService.getAccount(accountId))
                .thenReturn(new Account("12345", new BigDecimal("100"), new User("홍길동")));

        // 호출 (ack) + 결과 검증 (assert)
        mockMvc.perform(get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accountNumber").value("12345"))
                .andExpect(jsonPath("$.data.balance").value(100))
                .andExpect(jsonPath("$.data.owner.name").value("홍길동"))
        ;

        // Mock 을 한번 호출 여부 확인 (verify)
        verify(accountService, times(1)).getAccount(accountId);

    }

    @Test
    void deposit_정상입금() throws Exception {
        Long accountId = 1L;
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(new BigDecimal("100"));
        when(accountService.deposit(accountId, transactionRequestDTO.amount()))
                .thenReturn(new Account("12345", new BigDecimal("200"), new User("홍길동")));

        mockMvc.perform(post("/accounts/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accountNumber").value("12345"))
                .andExpect(jsonPath("$.data.balance").value(200))
                .andExpect(jsonPath("$.data.owner.name").value("홍길동"))
        ;

        verify(accountService, times(1)).deposit(accountId, transactionRequestDTO.amount());
    }

    @Test
    void withdraw_정상출금() throws Exception {
        Long accountId = 1L;
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(new BigDecimal("100"));
        when(accountService.withdraw(accountId, transactionRequestDTO.amount()))
                .thenReturn(new Account("12345", new BigDecimal("0"), new User("홍길동")));

        mockMvc.perform(post("/accounts/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accountNumber").value("12345"))
                .andExpect(jsonPath("$.data.balance").value(0))
                .andExpect(jsonPath("$.data.owner.name").value("홍길동"))
        ;

        verify(accountService, times(1)).withdraw(accountId, transactionRequestDTO.amount());
    }


}