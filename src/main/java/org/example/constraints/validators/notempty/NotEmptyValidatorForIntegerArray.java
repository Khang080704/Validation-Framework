package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForIntegerArray extends ConstraintValidator<int[]> {

    @Override
    public String validate(int[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
