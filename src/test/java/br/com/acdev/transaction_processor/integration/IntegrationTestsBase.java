package br.com.acdev.transaction_processor.integration;


import br.com.acdev.transaction_processor.model.Account;
import br.com.acdev.transaction_processor.repository.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import io.restassured.RestAssured;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestsBase {

    @Autowired
    private AccountRepository accountRepository;

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    private static Account defaultAccount;

    @BeforeAll
    static void beforeAll() {
        if (!postgres.isRunning()) {
            postgres.start();
        }
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        if (defaultAccount == null || accountRepository.findById(defaultAccount.getAccountId()).isEmpty()) {
            defaultAccount = createDefaultAccount();
        }
    }

    private Account createDefaultAccount() {
        Account account = new Account();
        account.setDocumentNumber("DEFAULT_ACCOUNT");
        account.setAccountLimit(BigDecimal.TEN);
        return accountRepository.save(account);
    }

    public Account getDefaultAccount() {
        return defaultAccount;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

}
