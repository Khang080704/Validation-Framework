package org.example.annotations.validator.number;

import org.example.annotations.Max;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.ValidatorResult;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class MaxAnnotationValidator implements AnnotationValidator<Max> {

    @Override
    public ValidatorResult validate(Max annotation, Object value, Field field) {
        long max = annotation.value();
        String message = annotation.message();

        if(value == null) return ValidatorResult.valid();

        Number inputValue = (Number)value;
        BigDecimal v = new BigDecimal(inputValue.toString());
        BigDecimal maxValue = BigDecimal.valueOf(max);

        boolean isValid = v.compareTo(maxValue) <= 0;
        if(!isValid && !message.isEmpty()) {
           return ValidatorResult.invalid(message);
        }
        if(!isValid) {
            return ValidatorResult.invalid(value + " must be less than or equal to " + maxValue);
        }
        return ValidatorResult.valid();

    }
}
