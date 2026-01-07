package org.example.providers;

import org.example.common.FieldValidator;

import java.util.Set;

public interface ClassValidatorProvider {
    Set<FieldValidator> getValidators(Class<?> type);
}
