package br.com.acdev.transaction_processor.integration;

import br.com.acdev.transaction_processor.controller.request.AccountRequest;
import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import br.com.acdev.transaction_processor.repository.TransactionRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static br.com.acdev.transaction_processor.utils.Messages.ACCOUNT_NOT_FOUND;
import static br.com.acdev.transaction_processor.utils.Messages.INVALID_LIMIT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ItAccountControllerTest extends IntegrationTestsBase{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void should_create_an_account() {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber("12345678900");
        given()
        .contentType(ContentType.JSON)
        .body(accountRequest)
        .when()
        .post("/accounts")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("document_number", equalTo("12345678900"));
    }

    @Test
    void should_not_create_an_account_with_invalid_limit() {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber("12345678900");
        accountRequest.setAccountLimit(BigDecimal.valueOf(100L).negate());
        given()
                .contentType(ContentType.JSON)
                .body(accountRequest)
                .when()
                .post("/accounts")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo(INVALID_LIMIT));
    }

    @Test
    void should_create_an_account_with_zero_limit() {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber("12345678900");
        accountRequest.setAccountLimit(BigDecimal.ZERO);
        given()
                .contentType(ContentType.JSON)
                .body(accountRequest)
                .when()
                .post("/accounts")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("document_number", equalTo(accountRequest.getDocumentNumber()));
    }

    @Test
    void should_read_an_account(){
        //given
        Account account = getDefaultAccount();
        //and
        given()
        .contentType(ContentType.JSON)
        .when()
        .get("/accounts/{id}", account.getAccountId())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("document_number", equalTo(account.getDocumentNumber()));
    }

    @Test
    void should_not_found_an_account(){
        //given
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        given()
        .contentType(ContentType.JSON)
        .when()
        .get("/accounts/{id}", 1)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo(ACCOUNT_NOT_FOUND));
    }
}
