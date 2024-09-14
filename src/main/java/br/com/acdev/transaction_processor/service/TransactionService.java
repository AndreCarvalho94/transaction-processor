package br.com.acdev.transaction_processor.service;

import br.com.acdev.transaction_processor.exceptions.InvalidAmountException;
import br.com.acdev.transaction_processor.exceptions.InvalidOperationTypeException;
import br.com.acdev.transaction_processor.exceptions.NoAvailableLimitForTransaction;
import br.com.acdev.transaction_processor.exceptions.NotFoundException;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.model.OperationType;
import br.com.acdev.transaction_processor.model.Transaction;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import br.com.acdev.transaction_processor.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.acdev.transaction_processor.utils.Messages.*;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Transactional
    public Transaction create(Transaction transaction, Long accountId) {
        Account account = getAccount(accountId);
        if(transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmountException(INVALID_AMOUNT);
        }
        configureTransaction(transaction, account);
        BigDecimal balance = accountService.readBalance(accountId);

        if(transaction.getOperationType() != OperationType.PAYMENT) {
            BigDecimal transactionBalancePreview = balance.add(transaction.getAmount());
            if (transactionBalancePreview.compareTo(BigDecimal.ZERO) < 0 &&
                    transactionBalancePreview.abs().compareTo(account.getAccountLimit()) > 0) {
                throw new NoAvailableLimitForTransaction(TOO_LOW_BALANCE);
            }
        }
        return repository.save(transaction);
    }

    private void configureTransaction(Transaction transaction, Account account) {
        transaction.setAccount(account);
        transaction.setEventDate(LocalDateTime.now());
        if(transaction.getOperationType() == OperationType.PAYMENT){
            transaction.setAmount(transaction.getAmount());
        }else{
            transaction.setAmount(transaction.getAmount().negate());
        }
    }

    private Account getAccount(Long accountId) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND));
        return account;
    }
}
