package org.example.providers;

import org.example.constraints.definition.ConstraintDefinition;
import org.example.constraints.definition.IsValidDefinition;
import org.example.constraints.validators.ConstraintValidator;
import org.example.constraints.validators.ConstraintValidatorRegistry;
import org.example.validators.ClassValidator;
import org.example.validators.ElementValidator;
import org.example.validators.FieldValidator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProgrammaticClassValidatorProvider implements ClassValidatorProvider{
    private final Map<Class<?>, ClassValidator> validators = new HashMap<>();

    @Override
    public ClassValidator getValidators(Class<?> type) {
        return validators.get(type);
    }

    public void createValidators(Class<?> type, Map<Field, Set<ConstraintDefinition>> constraints) {
        Set<ElementValidator> elementValidators = new HashSet<>();

        for (Map.Entry<Field, Set<ConstraintDefinition>> entry : constraints.entrySet()) {
            Set<ConstraintValidator<?>> constraintValidators = new HashSet<>();

            for (ConstraintDefinition definition : entry.getValue()) {
                if (definition instanceof IsValidDefinition) {
                    ClassValidator classValidator = this.validators.get(entry.getKey().getType());
                    if (classValidator != null) {
                        classValidator.setField(entry.getKey());
                        elementValidators.add(classValidator);
                    }
                } else {
                    ConstraintValidator<?> validator = ConstraintValidatorRegistry.getInstance(definition.getAnnotationType(), entry.getKey().getType());
                    validator.initialize(definition.getAttributes());
                    constraintValidators.add(validator);
                }
            }

            if (!constraintValidators.isEmpty()) {
                elementValidators.add(new FieldValidator(entry.getKey(), constraintValidators ));
            }
        }

        this.validators.put(type, new ClassValidator(elementValidators));
    }

    public void removeValidators(Class<?> type) {
        this.validators.remove(type);
    }
}
