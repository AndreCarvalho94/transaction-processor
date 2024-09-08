package br.com.acdev.transaction_processor.controller.request;

import br.com.acdev.transaction_processor.model.OperationType;
import br.com.acdev.transaction_processor.model.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionRequest {

    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("operation_type_id")
    private Integer operationTypeId;
    @JsonProperty("amount")
    private Long amount;

    public Transaction toTransaction(){
        return Transaction.builder()
                .operationType(OperationType.of(this.operationTypeId))
                .amount(this.amount)
                .build();
    }

}
