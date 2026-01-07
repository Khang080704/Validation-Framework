package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForString extends ConstraintValidator<String> {

    @Override
    public String validate(String value) {
        return (value == null || value.isEmpty()) ? message : null;
    }
}
