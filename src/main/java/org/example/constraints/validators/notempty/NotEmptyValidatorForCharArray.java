package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForCharArray extends ConstraintValidator<char[]> {

    @Override
    public String validate(char[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
