package br.com.acdev.transaction_processor.controller.response;


import br.com.acdev.transaction_processor.model.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    @JsonProperty("transaction_id")
    private Long transactionId;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("operation_type_id")
    private Integer operationTypeId;
    @JsonProperty("amount")
    private BigDecimal amount;

    public TransactionResponse(Transaction transaction){
        this.transactionId = transaction.getTransactionId();
        this.accountId = transaction.getAccount().getAccountId();
        this.operationTypeId = transaction.getOperationType().getOperationId();
        this.amount = transaction.getAmount().abs();
    }
}
