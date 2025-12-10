package org.example.concrete.NumberValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public record RangeValidator<T extends Number & Comparable<T>>(T min, T max) implements IValidator<T> {
    @Override
    public ValidatorResult validate(T value) {
        if (value == null) {
            return ValidatorResult.valid();
        }

        if(value.compareTo(min) >= 0 && value.compareTo(max) <= 0) {
            return ValidatorResult.valid();
        }
        return ValidatorResult.invalid(value + " is out of range");
    }
}
