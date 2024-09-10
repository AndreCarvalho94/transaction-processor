package br.com.acdev.transaction_processor.service;

import br.com.acdev.transaction_processor.exceptions.NotFoundException;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.model.OperationType;
import br.com.acdev.transaction_processor.model.Transaction;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import br.com.acdev.transaction_processor.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.com.acdev.transaction_processor.utils.MESSAGES.ACCOUNT_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountRepository accountRepository;

    @Transactional
    public Transaction create(Transaction transaction, Long accountId) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND));
        transaction.setAccount(account);
        transaction.setEventDate(LocalDateTime.now());
        if(transaction.getOperationType() == OperationType.PAYMENT){
            transaction.setAmount(transaction.getAmount().abs());
        }else{
            transaction.setAmount(transaction.getAmount().negate());
        }
        return repository.save(transaction);
    }
}
