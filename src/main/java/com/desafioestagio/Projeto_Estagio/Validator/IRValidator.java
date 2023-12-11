package com.desafioestagio.Projeto_Estagio.Validator;


import com.desafioestagio.Projeto_Estagio.entities.Monitorador;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = Validator_IR.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface  IRValidator {

   public String message() default "Rg invalido";

   public  Class<?>[] groups() default {};

   public Class<? extends Payload>[] payload() default {};


}
