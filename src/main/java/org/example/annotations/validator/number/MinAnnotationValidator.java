package org.example.annotations.validator.number;

import org.example.annotations.Min;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.ValidatorResult;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class MinAnnotationValidator implements AnnotationValidator<Min> {
    @Override
    public ValidatorResult validate(Min annotation, Object value, Field field) {
        int min = annotation.value();
        String message = annotation.message();

        if(value == null) return ValidatorResult.valid();

        Number inputValue = (Number)value;
        BigDecimal v = new BigDecimal(inputValue.toString());
        BigDecimal minValue = BigDecimal.valueOf(min);

        boolean isValid = v.compareTo(minValue) >= 0;
        if(!isValid && !message.isEmpty()) {
            return ValidatorResult.invalid(message);
        }
        if(!isValid) {
            return ValidatorResult.invalid(value + " must be greater than or equal to " + minValue);
        }
        return ValidatorResult.valid();

    }
}
