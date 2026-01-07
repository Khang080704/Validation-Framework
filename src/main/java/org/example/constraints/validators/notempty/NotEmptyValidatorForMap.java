package org.example.constraints.validators.notempty;

import org.example.constraints.validators.ConstraintValidator;

import java.util.Map;

public class NotEmptyValidatorForMap extends ConstraintValidator<Map<?, ?>> {

    @Override
    public String validate(Map<?, ?> value) {
        return (value == null || value.isEmpty()) ? message : null;
    }
}
