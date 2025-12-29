package org.example.annotations.validator.string;

import org.example.annotations.Pattern;
import org.example.annotations.core.AnnotationValidator;
import org.example.core.IValidator;
import org.example.core.ValidatorResult;
import org.example.validators.Basic.Regex.RegexValidator;

import java.lang.reflect.Field;

public class PatternAnnotationValidator implements AnnotationValidator<Pattern> {

    @Override
    public ValidatorResult validate(Pattern annotation, Object value, Field field) {
        if(value == null) return ValidatorResult.valid();

        String pattern = annotation.regex();
        String message = annotation.message();
        IValidator<String> regexValidator = new RegexValidator(pattern);
        ValidatorResult result = regexValidator.validate((String) value);

        if(!result.isValid() && !message.isEmpty()) {
            return ValidatorResult.invalid(message);
        }
        if(!result.isValid()) {
            return ValidatorResult.invalid(field.getName() + " is not a valid format");
        }
        return ValidatorResult.valid();
    }
}
