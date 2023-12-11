package com.desafioestagio.Projeto_Estagio.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Validator_IR implements ConstraintValidator<IRValidator, String> {


    @Override
    public void initialize(IRValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.length() > 1){
            return true;
        }
        return false;
    }
}
