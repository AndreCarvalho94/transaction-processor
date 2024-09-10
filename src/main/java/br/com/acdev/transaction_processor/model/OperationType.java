package br.com.acdev.transaction_processor.model;

import br.com.acdev.transaction_processor.exceptions.InvalidOperationTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static br.com.acdev.transaction_processor.utils.MESSAGES.INVALID_OPERATION_ID;

@Getter
@AllArgsConstructor
public enum OperationType{
    PURCHASE(1),
    INSTALLMENT_PURCHASE(2),
    WITHDRAW(3),
    PAYMENT(4);
    private final int operationId;

    static public OperationType of(int id){
        return Arrays.
                stream(OperationType.values())
                .filter(o->o.operationId == id)
                .findAny()
                .orElseThrow(()-> new InvalidOperationTypeException(INVALID_OPERATION_ID + id));
    }
}

