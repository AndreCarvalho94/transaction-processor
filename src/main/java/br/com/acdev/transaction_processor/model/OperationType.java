package br.com.acdev.transaction_processor.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OperationType{
    PURCHASE(1),
    INSTALLMENT_PURCHASE(2),
    WITHDRAW(3),
    PAYMENT(4);
    private int operationId;
}

