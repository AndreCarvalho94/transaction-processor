package br.com.acdev.transaction_processor.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String error;
    private int statusCode;
    private String details;

}
