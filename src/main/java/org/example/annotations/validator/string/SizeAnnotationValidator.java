package org.example.annotations.validator.string;

import org.example.annotations.Size;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.IValidator;
import org.example.core.ValidatorResult;
import org.example.validators.Basic.StringValidator.LengthValidator;

import java.lang.reflect.Field;
import java.util.Objects;

public class SizeAnnotationValidator implements AnnotationValidator<Size> {
    @Override
    public ValidatorResult validate(Size annotation, Object value, Field field) {
        int max = annotation.max();
        int min = annotation.min();
        String message = annotation.message();

        if(value == null) return ValidatorResult.valid();

        if(value instanceof String n) {
            IValidator<String> rangeValidator = new LengthValidator(min, max);
            ValidatorResult result = rangeValidator.validate(n);
            if(!Objects.equals(message, "") && !result.isValid()) {
                return ValidatorResult.invalid(message);
            }
            return result;
        }
        return ValidatorResult.invalid("@Size not supported for type " + field.getType());
    }
}
