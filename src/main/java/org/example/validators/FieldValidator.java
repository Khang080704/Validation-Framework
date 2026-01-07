package org.example.validators;

import org.example.common.ValidationViolation;
import org.example.constraints.validators.ConstraintValidator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FieldValidator extends ElementValidator {
    private final Set<ConstraintValidator<?>> validators;

    public FieldValidator(Field field, Set<ConstraintValidator<?>> validators) {
        this.field = field;
        this.field.setAccessible(true);
        this.validators = validators;
    }

    @Override
    public List<ValidationViolation> validate(Object value) {
        List<String> messages = new ArrayList<>();

        for (ConstraintValidator validator : validators) {
            String message = validator.validate(value);
            if (message != null) {
                messages.add(message);
            }
        }

        return messages.isEmpty()
            ? null
            : List.of(new ValidationViolation(field.getName(), messages));
    }
}
