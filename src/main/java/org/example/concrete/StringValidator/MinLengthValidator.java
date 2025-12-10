package org.example.concrete.StringValidator;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public class MinLengthValidator implements IValidator<String> {
    private int min;

    public MinLengthValidator(int min) {
        this.min = min;
    }

    @Override
    public ValidatorResult validate(String object) {
        if(object == null) return ValidatorResult.valid();

        if(object.length() >= min) return ValidatorResult.valid();
        return  ValidatorResult.invalid("object must be " + min + "characters long");
    }
}
