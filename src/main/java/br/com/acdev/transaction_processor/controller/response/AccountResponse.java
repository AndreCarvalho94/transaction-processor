package br.com.acdev.transaction_processor.controller.response;

import br.com.acdev.transaction_processor.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountResponse {

    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("document_number")
    private String documentNumber;

    @JsonProperty("account_limit")
    private BigDecimal accountLimit;

    public AccountResponse(Account account){
        this.accountId = account.getAccountId();
        this.documentNumber = account.getDocumentNumber();
        this.accountLimit = account.getAccountLimit();
    }
}
