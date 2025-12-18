package org.example.annotations.validator;

import org.example.annotations.Max;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.ValidatorResult;

import java.lang.reflect.Field;

public class MaxValidator implements AnnotationValidator<Max> {

    @Override
    public ValidatorResult validate(Max annotation, Object value, Field field) {
        long max = annotation.value();

        if (value instanceof Number n) {
            if (n.longValue() > max) {
                return ValidatorResult.invalid(
                        field.getName() + " must be <= " + max
                );
            }
        } else if (value instanceof String s) {
            if (s.length() > max) {
                return ValidatorResult.invalid(
                        field.getName() + " length must be <= " + max
                );
            }
        }
        else {
            return ValidatorResult.invalid(
                    "@Max not supported for type " + field.getType()
            );
        }

        return ValidatorResult.valid();
    }
}
