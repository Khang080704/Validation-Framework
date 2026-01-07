package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

import java.util.Collection;

public class NotEmptyValidatorForCollection extends ConstraintValidator<Collection<?>> {

    @Override
    public String validate(Collection<?> value) {
        return (value == null || value.isEmpty()) ? message : null;
    }
}
