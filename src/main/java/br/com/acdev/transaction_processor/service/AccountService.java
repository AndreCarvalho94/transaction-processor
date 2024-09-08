package br.com.acdev.transaction_processor.service;

import br.com.acdev.transaction_processor.exceptions.InvalidLimitException;
import br.com.acdev.transaction_processor.exceptions.NotFoundException;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.model.OperationType;
import br.com.acdev.transaction_processor.model.Transaction;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import java.math.BigDecimal;

import static br.com.acdev.transaction_processor.utils.Messages.ACCOUNT_NOT_FOUND;
import static br.com.acdev.transaction_processor.utils.Messages.INVALID_LIMIT;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    @Transactional
    public Account create(Account account) {
        if(account.getAccountLimit().compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidLimitException(INVALID_LIMIT);
        }
        return repository.save(account);
    }

    public Account read(Long id){
        return repository.findById(id).orElseThrow(()->new NotFoundException(ACCOUNT_NOT_FOUND));
    }

    public BigDecimal readBalance(Long id) {
        Account account = repository
                .findById(id)
                .orElseThrow(()->new NotFoundException("Account not found"));
        return account
                .getTransactions().stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
