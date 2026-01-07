package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForArray extends ConstraintValidator<Object[]> {

    @Override
    public String validate(Object[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
