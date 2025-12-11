package org.example.validators.Basic.StringValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public class RequireValidator<T> implements IValidator<T> {
    @Override
    public ValidatorResult validate(T value) {
        if(value == null) {
            return ValidatorResult.invalid("field is empty");
        }
        else {
            return ValidatorResult.valid();
        }
    }
}
