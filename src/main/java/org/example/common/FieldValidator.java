package org.example.common;

import org.example.constraints.validators.ConstraintValidator;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class FieldValidator {
    private Field field;
    private Set<ConstraintValidator<?>> validators = new HashSet<>();

    public FieldValidator(Field field, Set<ConstraintValidator<?>> validators) {
        this.field = field;
        this.field.setAccessible(true);
        this.validators = validators;
    }

    public Field getField() {
        return field;
    }

    public Set<ConstraintValidator<?>> getValidators() {
        return validators;
    }
}
