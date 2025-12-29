package org.example.annotations.validator.string;

import org.example.annotations.NotEmpty;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.ValidatorResult;

import java.lang.reflect.Field;

public class NotEmptyAnnotationValidator implements AnnotationValidator<NotEmpty> {
    @Override
    public ValidatorResult validate(NotEmpty annotation, Object value, Field field) {
        String message = annotation.message();

        if(value instanceof String n) {
            if(n.isEmpty() && !message.isEmpty()) {
                return ValidatorResult.invalid(message);
            } else if (n.isEmpty()) {
                return ValidatorResult.invalid("Value must not be empty");
            }
            return ValidatorResult.valid();
        }

        return ValidatorResult.invalid("value must not be empty");
    }
}
