package br.com.acdev.transaction_processor.service;

import br.com.acdev.transaction_processor.exceptions.NotFoundException;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    @Transactional
    public Account create(Account account) {
        return repository.save(account);
    }

    public Account read(Long id){
        return repository.findById(id).orElseThrow(()->new NotFoundException("Account not found"));
    }
}
