package com.tiendasgo.auth.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HorarioConfigValidator implements ConstraintValidator<ValidHorarioConfig, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        try {
            JsonNode root = OBJECT_MAPPER.readTree(value);
            if (!root.isObject()) {
                return violation(context, "horarioConfig debe ser un objeto JSON");
            }
            return true;
        } catch (Exception ex) {
            return violation(context, "horarioConfig no es un JSON valido");
        }
    }


    private boolean violation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}

