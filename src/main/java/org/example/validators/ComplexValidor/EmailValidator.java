package org.example.validators.ComplexValidor;

import org.example.core.IValidator;
import org.example.core.ValidatorResult;

public class EmailValidator implements IValidator<String> {
    @Override
    public ValidatorResult validate(String email) {
        if (!email.contains("@"))
            return ValidatorResult.invalid(email + " must contain @");

        String[] parts = email.split("@");
        if (parts.length != 2)
            return ValidatorResult.invalid(email + " must contain only one @");

        if (!parts[1].contains("."))
            return ValidatorResult.invalid(email + " must contain .");

        if (email.contains(" "))
            return ValidatorResult.invalid(email + " must not contain whitespace");

        // Optional: check domain exists
        return ValidatorResult.valid();
    }
}
