package org.example.concrete.StringValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public record LengthValidator (int min, int max) implements IValidator<String> {
    @Override
    public ValidatorResult validate(String object) {
        if(object == null) {
            return ValidatorResult.valid();
        }

        if(object.length() >= min && object.length() <= max) {
            return ValidatorResult.valid();
        }
        return ValidatorResult.invalid(object + "length is must be between " + min + " and " + max);
    }
}
