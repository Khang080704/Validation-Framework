package org.example.validators.Basic.StringValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public class UpperCaseValidator implements IValidator<String> {
    @Override
    public ValidatorResult validate(String object) {
        if(!object.chars().anyMatch(Character::isUpperCase)) {
            return ValidatorResult.invalid(object + " must contain uppercase characters");
        }
        return ValidatorResult.valid();
    }
}
