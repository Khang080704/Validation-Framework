package org.example.constraints.validators;

import org.example.config.NotNullConfig;


public class NotNullValidator extends ConstraintValidator<Object> {

    @Override
    public String validate(Object value) {
        return value == null ? message : null;
    }
}
