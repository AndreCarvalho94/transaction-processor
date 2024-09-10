package br.com.acdev.transaction_processor.integration;

import br.com.acdev.transaction_processor.controller.request.AccountRequest;
import br.com.acdev.transaction_processor.controller.request.TransactionRequest;
import br.com.acdev.transaction_processor.controller.response.AccountResponse;
import br.com.acdev.transaction_processor.controller.response.TransactionResponse;
import br.com.acdev.transaction_processor.model.Transaction;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import br.com.acdev.transaction_processor.repository.TransactionRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static br.com.acdev.transaction_processor.model.OperationType.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ItUseCasesTest extends IntegrationTestsBase{


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void should_create_account_and_withdraw_transactions_and_verify_transaction_negative_values_on_db(){
        accountRepository.deleteAll();
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber("TEST_ACCOUNT");
        AccountResponse accountResponse = createAccount(accountRequest);
        BigDecimal transactionValue = BigDecimal.TEN;
        TransactionRequest requestWithdraw = new TransactionRequest(accountResponse.getAccountId(), WITHDRAW.getOperationId(), transactionValue);
        TransactionResponse responseWithdraw = createTransaction(requestWithdraw);
        Optional<Transaction> transactionWithdraw = transactionRepository.findById(responseWithdraw.getTransactionId());
        Assertions.assertTrue(transactionWithdraw.isPresent());
        Assertions.assertEquals(transactionValue.negate().setScale(2, RoundingMode.HALF_UP), transactionWithdraw.get().getAmount());
    }

    @Test
    void should_create_account_and_purchase_transactions_and_verify_transaction_negative_values_on_db(){
        accountRepository.deleteAll();
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber("TEST_ACCOUNT");
        AccountResponse accountResponse = createAccount(accountRequest);
        BigDecimal transactionValue = BigDecimal.TEN;
        TransactionRequest requestPurchase = new TransactionRequest(accountResponse.getAccountId(), PURCHASE.getOperationId(), transactionValue);
        TransactionResponse responsePurchase = createTransaction(requestPurchase);
        Optional<Transaction> transactionPurchase = transactionRepository.findById(responsePurchase.getTransactionId());
        Assertions.assertTrue(transactionPurchase.isPresent());
        Assertions.assertEquals(transactionValue.negate().setScale(2, RoundingMode.HALF_UP), transactionPurchase.get().getAmount());
    }

    @Test
    void should_create_account_and_installment_purchase_transactions_and_verify_transaction_negative_values_on_db(){
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber("TEST_ACCOUNT");
        AccountResponse accountResponse = createAccount(accountRequest);
        BigDecimal transactionValue = BigDecimal.TEN;
        TransactionRequest requestInstallmentPurchase = new TransactionRequest(accountResponse.getAccountId(), INSTALLMENT_PURCHASE.getOperationId(), transactionValue);
        TransactionResponse responseInstallmentPurchase = createTransaction(requestInstallmentPurchase);
        Optional<Transaction> transactionInstallmentPurchase = transactionRepository.findById(responseInstallmentPurchase.getTransactionId());
        Assertions.assertTrue(transactionInstallmentPurchase.isPresent());
        Assertions.assertEquals(transactionValue.negate().setScale(2, RoundingMode.HALF_UP), transactionInstallmentPurchase.get().getAmount());
    }

    @Test
    void should_create_account_and_payment_transactions_and_verify_transaction_positive_values_on_db(){
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber("TEST_ACCOUNT");
        AccountResponse accountResponse = createAccount(accountRequest);
        BigDecimal transactionValue = BigDecimal.TEN;
        TransactionRequest requestPayment = new TransactionRequest(accountResponse.getAccountId(), PAYMENT.getOperationId(), transactionValue);
        TransactionResponse responsePayment = createTransaction(requestPayment);
        Optional<Transaction> transactionPayment = transactionRepository.findById(responsePayment.getTransactionId());
        Assertions.assertTrue(transactionPayment.isPresent());
        Assertions.assertEquals(transactionValue.setScale(2, RoundingMode.HALF_UP), transactionPayment.get().getAmount());
    }

    private TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        return given()
        .contentType(ContentType.JSON)
        .body(transactionRequest)
        .when()
        .post("/transactions")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract().as(TransactionResponse.class);
    }

    private AccountResponse createAccount(AccountRequest accountRequest) {
        return given()
        .contentType(ContentType.JSON)
        .body(accountRequest)
        .when()
        .post("/accounts")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract().as(AccountResponse.class);
    }
}
