package br.com.acdev.transaction_processor.controller;


import br.com.acdev.transaction_processor.controller.response.ErrorResponse;
import br.com.acdev.transaction_processor.exceptions.InvalidAmountException;
import br.com.acdev.transaction_processor.exceptions.InvalidLimitException;
import br.com.acdev.transaction_processor.exceptions.InvalidOperationTypeException;
import br.com.acdev.transaction_processor.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOperationTypeException.class)
    public ResponseEntity<ErrorResponse> invalidOperationType(InvalidOperationTypeException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorResponse> invalidAmount(InvalidAmountException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLimitException.class)
    public ResponseEntity<ErrorResponse> invalidLimit(InvalidLimitException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> global(Exception ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
