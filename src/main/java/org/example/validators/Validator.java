package org.example.validators;

import org.example.common.ValidationViolation;
import org.example.providers.ClassValidatorProvider;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    List<ClassValidatorProvider> providers;

    public Validator(List<ClassValidatorProvider> providers) {
        this.providers = providers;
    }

    public List<ValidationViolation> validate(Object object) {
        List<ValidationViolation> result = new ArrayList<>();

        for (ClassValidatorProvider provider : providers) {
            ClassValidator validator = provider.getValidators(object.getClass());

            if (validator != null) {
                List<ValidationViolation> violations = validator.validate(object);

                if (violations != null) {
                    for (ValidationViolation violation : violations) {
                        addViolation(result, violation);
                    }
                }
            }
        }

        return result.isEmpty() ? null : result;
    }

    public void addViolation(List<ValidationViolation> violations, ValidationViolation violationToAdd) {
        for (ValidationViolation violation : violations) {
            if (violation.getPath().equals(violationToAdd.getPath())) {
                violation.getMessages().addAll(violationToAdd.getMessages());
                return;
            }
        }

        violations.add(violationToAdd);
    }
}
