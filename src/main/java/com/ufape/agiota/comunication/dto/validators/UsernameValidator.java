package com.ufape.agiota.comunication.dto.validators;

import com.ufape.agiota.comunication.dto.tags.Username;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UsernameValidator implements ConstraintValidator<Username, String> {

    // Regex atualizado que inclui o ponto
    private static final String userRegex = "^[a-zA-Z][a-zA-Z0-9._]{5,25}+$";


    @Override
    public void initialize(Username constraintAnnotation) {
        // Pode ser utilizado para inicializações, se necessário
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        return username.matches(userRegex);
    }
}
