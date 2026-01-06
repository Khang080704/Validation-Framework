package org.example.providers;

import org.example.common.FieldValidator;
import org.example.constraints.validators.ConstraintValidator;

import java.util.Map;
import java.util.Set;

public class ProgrammaticClassValidatorProvider implements ClassValidatorProvider{
    private Map<Class<?>, Set<FieldValidator>> validators;

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
