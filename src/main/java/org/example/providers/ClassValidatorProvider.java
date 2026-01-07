package org.example.providers;

import org.example.validators.ClassValidator;

public interface ClassValidatorProvider {
    ClassValidator getValidators(Class<?> type);
}
