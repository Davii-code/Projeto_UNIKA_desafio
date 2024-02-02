package com.desafioestagio.Projeto_Estagio.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class ErrorResponse  extends RuntimeException{
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
    public ErrorResponse(){}

    public  ErrorResponse fromViolations(Set<ConstraintViolation<?>> violations) {
        ErrorResponse errorResponse = new ErrorResponse();

        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            errorMessage.append(violation.getMessage()).append("; ");
        }

        errorResponse.setMessage(errorMessage.toString());
        return errorResponse;
    }

    private void setMessage(String string) {
    }

    public String getMessage() {
        return message;
    }

}
