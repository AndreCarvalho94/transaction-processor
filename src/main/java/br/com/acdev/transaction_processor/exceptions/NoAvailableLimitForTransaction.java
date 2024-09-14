package br.com.acdev.transaction_processor.exceptions;

public class NoAvailableLimitForTransaction extends RuntimeException {
    public NoAvailableLimitForTransaction(String msg) {
        super(msg);
    }
}
