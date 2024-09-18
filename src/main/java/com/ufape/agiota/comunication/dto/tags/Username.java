package com.ufape.agiota.comunication.dto.tags;


import com.ufape.agiota.comunication.dto.validators.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define a anotação @Username
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {
    String message() default "Username inválido";  // Mensagem padrão

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
