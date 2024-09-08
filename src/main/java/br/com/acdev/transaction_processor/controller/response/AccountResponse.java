package br.com.acdev.transaction_processor.controller.response;

import br.com.acdev.transaction_processor.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {

    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("document_number")
    private String documentNumber;

    public AccountResponse(Account account){
        this.accountId = account.getAccountId();
        this.documentNumber = account.getDocumentNumber();
    }
}
