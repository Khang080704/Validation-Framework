package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForBooleanArray extends ConstraintValidator<boolean[]> {

    @Override
    public String validate(boolean[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
