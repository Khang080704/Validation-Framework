package org.example.providers;

import org.example.common.FieldValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProgrammaticClassValidatorProvider implements ClassValidatorProvider{
    private final Map<Class<?>, Set<FieldValidator>> validators = new HashMap<>();

    @Override
    public Set<FieldValidator> getValidators(Class<?> type) {
        return validators.get(type);
    }

    public void putValidators(Class<?> type, Set<FieldValidator> validators) {
        this.validators.put(type, validators);
    }

    public void removeValidators(Class<?> type) {
        this.validators.remove(type);
    }
}
