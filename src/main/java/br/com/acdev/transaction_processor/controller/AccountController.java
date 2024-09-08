package br.com.acdev.transaction_processor.controller;

import br.com.acdev.transaction_processor.controller.request.AccountRequest;
import br.com.acdev.transaction_processor.controller.response.AccountResponse;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody AccountRequest accountRequest){
        Account account = service.create(accountRequest.toAccount());
        return ResponseEntity.ok(new AccountResponse(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> read(@PathVariable Long id){
        Account account = service.read(id);
        return ResponseEntity.ok(new AccountResponse(account));
    }
}
