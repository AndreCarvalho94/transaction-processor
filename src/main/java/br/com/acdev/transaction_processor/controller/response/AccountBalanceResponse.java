package br.com.acdev.transaction_processor.controller.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountBalanceResponse {
    @JsonProperty("account_id")
    private Long accountId;
    private BigDecimal balance;
}
