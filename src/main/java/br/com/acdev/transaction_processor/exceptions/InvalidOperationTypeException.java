package br.com.acdev.transaction_processor.exceptions;

public class InvalidOperationTypeException extends RuntimeException{


    public InvalidOperationTypeException(String msg){
        super(msg);
    }
}
