package br.com.acdev.transaction_processor.controller;

import br.com.acdev.transaction_processor.controller.request.TransactionRequest;
import br.com.acdev.transaction_processor.controller.response.TransactionResponse;
import br.com.acdev.transaction_processor.model.Transaction;
import br.com.acdev.transaction_processor.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody TransactionRequest request){
        Transaction transaction = request.toTransaction();
        Transaction savedTransaction = service.create(transaction, request.getAccountId());
        return ResponseEntity.ok(new TransactionResponse(savedTransaction));
    }
}
