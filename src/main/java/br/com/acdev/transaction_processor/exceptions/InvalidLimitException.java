package br.com.acdev.transaction_processor.exceptions;

public class InvalidLimitException extends RuntimeException {
    public InvalidLimitException(String msg) {
        super(msg);
    }
}
