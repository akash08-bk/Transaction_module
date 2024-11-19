package com.bank.transaction.service;

import com.bank.transaction.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    Optional<Transaction> getTransactionById(Long id);

    List<Transaction> getAllTransactions();

    Transaction updateTransactionStatus(Long id, String status);

    boolean deleteTransaction(Long id);
}
