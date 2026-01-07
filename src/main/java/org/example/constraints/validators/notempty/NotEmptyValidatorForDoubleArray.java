package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForDoubleArray extends ConstraintValidator<double[]> {

    @Override
    public String validate(double[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
