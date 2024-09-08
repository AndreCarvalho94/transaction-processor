package br.com.acdev.transaction_processor.controller.request;

import br.com.acdev.transaction_processor.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @JsonProperty("document_number")
    private String documentNumber;


    public Account toAccount(){
        return Account.builder().documentNumber(this.documentNumber).build();
    }
}
