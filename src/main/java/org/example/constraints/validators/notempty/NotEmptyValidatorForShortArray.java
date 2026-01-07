package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForShortArray extends ConstraintValidator<short[]> {

    @Override
    public String validate(short[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
