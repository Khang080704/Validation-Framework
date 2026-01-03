package org.example.validators;

import org.example.common.ValidationViolation;

public interface IValidator {
    ValidationViolation validate(Object object);
    ValidationViolation validateProperty(Object object, String property);
}
