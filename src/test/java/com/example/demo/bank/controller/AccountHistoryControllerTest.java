package com.example.demo.bank.controller;

import com.example.demo.bank.domain.Account;
import com.example.demo.bank.domain.AccountHistory;
import com.example.demo.bank.service.AccountHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountHistoryController.class)
class AccountHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountHistoryService accountHistoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAccountHistory_정상조회() throws Exception {
        Long accountId = 1L;
        Account account = new Account();
        when(accountHistoryService.getAccountHistories(accountId))
                .thenReturn(List.of(
                        new AccountHistory(account, AccountHistory.TransType.DEPOSIT, new BigDecimal("200")),
                        new AccountHistory(account, AccountHistory.TransType.WITHDRAW, new BigDecimal("100"))
                ));

        mockMvc.perform(get("/accounts/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].amount").value(200))
                .andExpect(jsonPath("$.data[0].transType").value("DEPOSIT"))
                .andExpect(jsonPath("$.data[1].amount").value(100))
                .andExpect(jsonPath("$.data[1].transType").value("WITHDRAW"))
        ;

        // Mock 을 한번 호출했는지 확인 (verify)
        verify(accountHistoryService, times(1)).getAccountHistories(accountId);
    }
}