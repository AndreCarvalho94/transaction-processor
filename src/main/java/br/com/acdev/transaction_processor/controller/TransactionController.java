package br.com.acdev.transaction_processor.controller;

import br.com.acdev.transaction_processor.controller.request.TransactionRequest;
import br.com.acdev.transaction_processor.controller.response.AccountResponse;
import br.com.acdev.transaction_processor.controller.response.ErrorResponse;
import br.com.acdev.transaction_processor.controller.response.TransactionResponse;
import br.com.acdev.transaction_processor.model.Transaction;
import br.com.acdev.transaction_processor.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction Created", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }) })
    @Operation(summary = "Create a new account")
    public ResponseEntity<TransactionResponse> create(@RequestBody TransactionRequest request){
        Transaction transaction = request.toTransaction();
        Transaction savedTransaction = service.create(transaction, request.getAccountId());
        return ResponseEntity.ok(new TransactionResponse(savedTransaction));
    }
}
