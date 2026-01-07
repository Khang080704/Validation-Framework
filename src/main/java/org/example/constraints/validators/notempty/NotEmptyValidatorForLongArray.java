package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForLongArray extends ConstraintValidator<long[]> {

    @Override
    public String validate(long[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
