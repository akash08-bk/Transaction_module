package com.bank.transaction;

import com.bank.transaction.controller.TransactionController;
import com.bank.transaction.model.Transaction;
import com.bank.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTransaction() throws Exception {
        // Create a mock Transaction object
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setAccountNumber("1234567890");
        mockTransaction.setAmount(500.0);
        mockTransaction.setType("credit");
        mockTransaction.setStatus("pending");

        // Mock the service response
        Mockito.when(transactionService.createTransaction(Mockito.any(Transaction.class)))
                .thenReturn(mockTransaction);

        // Create a JSON request body
        Transaction requestTransaction = new Transaction();
        requestTransaction.setAccountNumber("1234567890");
        requestTransaction.setAmount(500.0);
        requestTransaction.setType("credit");
        requestTransaction.setStatus("pending");

        String jsonRequestBody = objectMapper.writeValueAsString(requestTransaction);

        // Perform POST request and assert the response
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.amount").value(500.0))
                .andExpect(jsonPath("$.type").value("credit"))
                .andExpect(jsonPath("$.status").value("pending"));
    }
}
