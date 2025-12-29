package org.example.annotations.validator.string;

import org.example.annotations.Email;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.IValidator;
import org.example.core.ValidatorResult;
import org.example.validators.ComplexValidor.EmailValidator;

import java.lang.reflect.Field;
import java.util.Objects;

public class EmailAnnotationValidator implements AnnotationValidator<Email> {
    @Override
    public ValidatorResult validate(Email annotation, Object value, Field field) {
        if(value == null) return ValidatorResult.valid();

        final String email = (String) value;
        String message = annotation.message();

        IValidator<String> validator = new EmailValidator();
        ValidatorResult result = validator.validate(email);

        if(!Objects.equals(message, "") &&  !result.isValid()){
            return ValidatorResult.invalid(message);
        }

        return result;
    }
}
