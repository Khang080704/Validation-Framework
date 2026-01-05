package org.example.validators.constraintvalidators;

import org.example.config.NotEmptyConfig;

public class NotEmptyValidator implements ConstraintValidator<NotEmptyConfig, String>{

    @Override
    public void initialize(NotEmptyConfig config) {
        ConstraintValidator.super.initialize(config);
    }

    @Override
    public boolean isValid(String value) {
        if(value == null){
            return true;
        }
        return !value.isEmpty();
    }
}
