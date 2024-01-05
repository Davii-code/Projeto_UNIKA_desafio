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
        if (s == null) {
            // Trata o caso em que a string é nula
            return true; // Ou false, dependendo da lógica que você deseja
        }

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }}



