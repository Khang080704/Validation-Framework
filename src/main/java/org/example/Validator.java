package org.example;

import org.example.common.FieldValidator;
import org.example.common.ValidationViolation;
import org.example.constraints.validators.ConstraintValidator;
import org.example.providers.ClassValidatorProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Validator {
    List<ClassValidatorProvider> providers;

    public Validator(List<ClassValidatorProvider> providers) {
        this.providers = providers;
    }

    public ValidationViolation validate(Object object) {
        try {
            ValidationViolation violation = new ValidationViolation();
            Set<FieldValidator> validators = combineValidators(object);

            for (FieldValidator fieldValidator : validators) {
                List<String> messages = new ArrayList<>();

                for (ConstraintValidator constraintValidator : fieldValidator.getValidators()) {
                    Object value = fieldValidator.getField().get(object);
                    String message = constraintValidator.validate(value);

                    if (message != null) {
                        messages.add(message);
                    }
                }

                if (!messages.isEmpty()) {
                    violation.addViolation(fieldValidator.getField().getName(), messages);
                }
            }

            return violation;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<FieldValidator> combineValidators(Object object) {
        Set<FieldValidator> result = new HashSet<>();

        for (ClassValidatorProvider provider : providers) {
            Set<FieldValidator> validators = provider.getValidators(object.getClass());
            if (validators != null) {
                result.addAll(validators);
            }
        }

        return result;
    }
}
