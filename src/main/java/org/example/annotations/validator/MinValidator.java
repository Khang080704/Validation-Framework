package org.example.annotations.validator;

import org.example.annotations.Min;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.ValidatorResult;

import java.lang.reflect.Field;

public class MinValidator implements AnnotationValidator<Min> {
    @Override
    public ValidatorResult validate(Min annotation, Object value, Field field) {
        long min = annotation.value();

        if (value instanceof Number n) {
            if (n.longValue() < min) {
                return ValidatorResult.invalid(
                        field.getName() + " must be >= " + min
                );
            }
        } else if (value instanceof String s) {
            if (s.length() < min) {
                return ValidatorResult.invalid(
                        field.getName() + " length must be >= " + min
                );
            }
        }
        else {
            return ValidatorResult.invalid(
                    "@Min not supported for type " + field.getType()
            );
        }

        return ValidatorResult.valid();
    }
}
