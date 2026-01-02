package org.example.validators.constraintvalidators;

import org.example.config.NotNullConfig;
import org.example.constraints.NotNull;


public class NotNullValidator implements ConstraintValidator<NotNullConfig, Object> {

    @Override
    public boolean isValid(Object value) {
        return value != null;
    }
}
