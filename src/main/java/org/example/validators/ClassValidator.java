package org.example.validators;

import org.example.common.ValidationViolation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassValidator extends ElementValidator {
    private final Set<ElementValidator> validators;

    public ClassValidator(Set<ElementValidator> validators) {
        this.validators = validators;
    }

    public ClassValidator(Field field, Set<ElementValidator> validators) {
        this.field = field;
        this.field.setAccessible(true);
        this.validators = validators;
    }

    @Override
    public List<ValidationViolation> validate(Object value) {
        List<ValidationViolation> results = new ArrayList<>();

        for (ElementValidator validator : validators) {
            try {
                validator.getField().setAccessible(true);
                Object fieldValue = validator.getField().get(value);
                List<ValidationViolation> violations = validator.validate(fieldValue);
                if (violations != null) {
                    if (this.field != null) {
                        for (ValidationViolation violation : violations) {
                            violation.setPath(this.field.getName() + "." + violation.getPath());
                        }
                    }
                    results.addAll(violations);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return results.isEmpty() ? null : results;
    }
}
