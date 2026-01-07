package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForFloatArray extends ConstraintValidator<float[]> {

    @Override
    public String validate(float[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
