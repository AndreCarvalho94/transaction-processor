package br.com.acdev.transaction_processor.controller;

import br.com.acdev.transaction_processor.controller.request.AccountRequest;
import br.com.acdev.transaction_processor.controller.response.AccountBalanceResponse;
import br.com.acdev.transaction_processor.controller.response.AccountResponse;
import br.com.acdev.transaction_processor.controller.response.ErrorResponse;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    ///CONTA COM LIMITE DE CREDITO
    //PERMITIR OPERAÇOES ATE ATIGNIR ESSE LIMITE
    private final AccountService service;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created account", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponse.class)) }),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }) })
    @Operation(summary = "Create a new account")
    public ResponseEntity<AccountResponse> create(@RequestBody AccountRequest accountRequest){
        Account account = service.create(accountRequest.toAccount());
        return ResponseEntity.ok(new AccountResponse(account));
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created account", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Account not found", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }) })
    @Operation(summary = "Read Account")
    public ResponseEntity<AccountResponse> read(@PathVariable Long id){
        Account account = service.read(id);
        return ResponseEntity.ok(new AccountResponse(account));
    }

    @GetMapping("{id}/balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account balance", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Account not found", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)) }) })
    @Operation(summary = "Read Account Balance")
    public ResponseEntity<AccountBalanceResponse> readBalance(@PathVariable Long id){
        BigDecimal balance = service.readBalance(id);
        return ResponseEntity.ok(new AccountBalanceResponse(id, balance));
    }
}
