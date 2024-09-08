package br.com.acdev.transaction_processor.service;

import br.com.acdev.transaction_processor.exceptions.NotFoundException;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.model.Transaction;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import br.com.acdev.transaction_processor.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountRepository accountRepository;

    @Transactional
    public Transaction create(Transaction transaction, Long accountId) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        transaction.setAccount(account);
        transaction.setEventDate(LocalDateTime.now());
        return repository.save(transaction);
    }
}
