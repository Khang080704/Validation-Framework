package org.example.validators.Basic.NumberValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public record MinValidator<T extends Number & Comparable<T>>(T min) implements IValidator<T> {
    @Override
    public ValidatorResult validate(T object) {
        if (object == null) {
            return ValidatorResult.valid();
        }

        if(object.compareTo(min) >= 0) {
            return ValidatorResult.valid();
        }
        return ValidatorResult.invalid(object.toString() + " must be greater than or equal to " + min);
    }
}
