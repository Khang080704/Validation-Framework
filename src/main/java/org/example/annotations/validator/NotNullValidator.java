package org.example.annotations.validator;

import org.example.annotations.NotNull;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.ValidatorResult;

import java.lang.reflect.Field;

public class NotNullValidator implements AnnotationValidator<NotNull> {

    @Override
    public ValidatorResult validate(NotNull annotation, Object value, Field field) {
        if(value == null){
            return ValidatorResult.invalid(field.getName() + " must not be null");
        }
        return ValidatorResult.valid();
    }
}
