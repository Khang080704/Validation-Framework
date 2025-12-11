package org.example.validators.Basic.NumberValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public record MaxValidator<T extends Number & Comparable<T>>(T max) implements IValidator<T> {
    @Override
    public ValidatorResult validate(T object) {
        if(object == null) {
            return ValidatorResult.valid();
        }
        if(object.compareTo(max) <= 0) {
            return ValidatorResult.valid();
        }
        return ValidatorResult.invalid(object.toString() + " must be less than or equal to " + max);
    }
}
