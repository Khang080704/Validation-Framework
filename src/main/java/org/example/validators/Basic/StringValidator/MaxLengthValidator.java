package org.example.validators.Basic.StringValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public class MaxLengthValidator implements IValidator<String> {
    private int max;

    public MaxLengthValidator(int max) {
        this.max = max;
    }

    @Override
    public ValidatorResult validate(String object) {
        if (object == null) return ValidatorResult.valid();
        if (object.length() < max) return ValidatorResult.valid();
        return ValidatorResult.invalid("object must be " + max + "characters long");
    }
}
