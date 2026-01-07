package org.example.validators;

import org.example.common.ValidationViolation;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ElementValidator {
    protected Field field;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public abstract List<ValidationViolation> validate(Object value);
}
