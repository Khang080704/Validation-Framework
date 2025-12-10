package org.example.concrete.StringValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public class RequireValidator implements IValidator<String> {
    @Override
    public ValidatorResult validate(String value) {
        if(value.isEmpty()) {
            return ValidatorResult.invalid("field is empty");
        }
        else {
            return ValidatorResult.valid();
        }
    }
}
