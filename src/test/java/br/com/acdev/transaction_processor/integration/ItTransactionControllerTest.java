package br.com.acdev.transaction_processor.integration;

import br.com.acdev.transaction_processor.controller.request.TransactionRequest;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.model.OperationType;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import br.com.acdev.transaction_processor.repository.TransactionRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static br.com.acdev.transaction_processor.model.OperationType.*;
import static br.com.acdev.transaction_processor.utils.Messages.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ItTransactionControllerTest extends IntegrationTestsBase{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void should_create_payment_transaction(){
        //given
        Account account = getDefaultAccount();
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                account.getAccountId(),
                OperationType.PAYMENT.getOperationId(),
                BigDecimal.TEN);
        //and
        given()
        .contentType(ContentType.JSON)
        .body(transactionRequest)
        .when()
        .post("/transactions")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        //.body("amount", equalTo(BigDecimal.TEN)) framework is not working fine with bigdecimal types
        .body("operation_type_id", equalTo(PAYMENT.getOperationId()))
        //.body("account_id", equalTo(account.getAccountId().toString()))
        .body("transaction_id", notNullValue());
    }
    @Test
    void should_create_withdraw_transaction(){
        //given
        Account savedAccount = getDefaultAccount();
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                savedAccount.getAccountId(),
                WITHDRAW.getOperationId(),
                BigDecimal.TEN);
        //and
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequest)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                //.body("amount", equalTo(BigDecimal.TEN)) framework is not working fine with bigdecimal types
                .body("operation_type_id", equalTo(WITHDRAW.getOperationId()))
                //.body("account_id", contains(savedAccount.getAccountId().intValue()))
                .body("transaction_id", notNullValue());
    }

    @Test
    void should_create_purchase_transaction(){
        //given
        Account savedAccount = getDefaultAccount();
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                savedAccount.getAccountId(),
                PURCHASE.getOperationId(),
                BigDecimal.TEN);
        //and
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequest)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                //.body("amount", equalTo(BigDecimal.TEN)) framework is not working fine with bigdecimal types
                .body("operation_type_id", equalTo(PURCHASE.getOperationId()))
                //.body("account_id", contains(savedAccount.getAccountId().intValue()))
                .body("transaction_id", notNullValue());
    }

    @Test
    void should_create_installment_purchase_transaction(){
        //given
        Account savedAccount = getDefaultAccount();
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                savedAccount.getAccountId(),
                INSTALLMENT_PURCHASE.getOperationId(),
                BigDecimal.TEN);
        //and
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequest)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                //.body("amount", equalTo(BigDecimal.TEN)) framework is not working fine with bigdecimal types
                .body("operation_type_id", equalTo(INSTALLMENT_PURCHASE.getOperationId()))
                //.body("account_id", contains(savedAccount.getAccountId().intValue()))
                .body("transaction_id", notNullValue());
    }

    @Test
    void should_not_create_invalid_transaction(){
        //given
        Account savedAccount = getDefaultAccount();
        int operationtype = 5;
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                savedAccount.getAccountId(),
                operationtype,
                BigDecimal.TEN);
        //and
        given()
        .contentType(ContentType.JSON)
        .body(transactionRequest)
        .when()
        .post("/transactions")
        .then()
        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo(INVALID_OPERATION_ID + operationtype));
    }

    @Test
    void should_not_create_transaction_with_invalid_amount(){
        //given
        Account savedAccount = getDefaultAccount();
        int operationtype = 1;
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                savedAccount.getAccountId(),
                operationtype,
                BigDecimal.TEN.negate());
        //and
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequest)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo(INVALID_AMOUNT));
    }

    @Test
    void should_not_create_transaction_with_amount_zero(){
        //given
        Account savedAccount = getDefaultAccount();
        int operationtype = 1;
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                savedAccount.getAccountId(),
                operationtype,
                BigDecimal.ZERO);
        //and
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequest)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo(INVALID_AMOUNT));
    }

    @Test
    void should_not_create_transaction_with_too_low_limit_for_operation(){
        transactionRepository.deleteAll();
        //given
        Account savedAccount = getDefaultAccount();
        int operationtype = 1;
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                savedAccount.getAccountId(),
                operationtype,
                savedAccount.getAccountLimit().add(BigDecimal.ONE));
        //and
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequest)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo(TOO_LOW_BALANCE));
    }

    @Test
    void should_not_create_transaction_with_too_low_limit_for_operation_2(){
        transactionRepository.deleteAll();
        //given
        Account savedAccount = getDefaultAccount();
        int operationtype = 1;
        //and
        TransactionRequest transactionRequest = new TransactionRequest(
                savedAccount.getAccountId(),
                operationtype,
                savedAccount.getAccountLimit().divide(BigDecimal.TWO));
        //and
        given()
                .contentType(ContentType.JSON)
                .body(transactionRequest)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo(TOO_LOW_BALANCE));
    }

}
