package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

public class NotEmptyValidatorForByteArray extends ConstraintValidator<byte[]> {

    @Override
    public String validate(byte[] value) {
        return (value == null || value.length == 0) ? message : null;
    }
}
