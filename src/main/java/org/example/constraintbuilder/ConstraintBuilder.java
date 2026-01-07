package org.example.constraintbuilder;

import org.example.common.FieldValidator;
import org.example.constraints.definition.ConstraintDefinition;
import org.example.constraints.validators.ConstraintValidator;
import org.example.constraints.validators.ConstraintValidatorRegistry;
import org.example.providers.ProgrammaticClassValidatorProvider;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ConstraintBuilder {
    private Class<?> type;
    private final Set<FieldValidator> validators = new HashSet<>();
    private final ProgrammaticClassValidatorProvider provider;

    public ConstraintBuilder(ProgrammaticClassValidatorProvider provider) {
        this.provider = provider;
    }

    public ConstraintBuilder on(Class<?> type) {
        this.type = type;
        return this;
    }

    public ConstraintBuilder constraints(String fieldName, ConstraintDefinition... defs) {
        if (this.type == null) {
            throw new IllegalStateException("Type must be set before adding constraints.");
        }

        Field field = this.getField(fieldName);

        if (defs.length == 0) {
            throw new IllegalArgumentException("At least one constraint definition must be provided.");
        }

        Set<ConstraintValidator<?>> constraintValidators = new HashSet<>();

        for (ConstraintDefinition def : defs) {
            ConstraintValidator<?> validator = ConstraintValidatorRegistry.getInstance(def.getAnnotationType(), field.getType());
            validator.initialize(def.getAttributes());
            constraintValidators.add(validator);
        }

        this.validators.add(new FieldValidator(field, constraintValidators));

        return this;
    }

    public void build() {
        if (this.type == null) {
            throw new IllegalStateException("Type must be set before building configurations.");
        }

        if (this.validators.isEmpty()) {
            throw new IllegalStateException("At least one field configuration must be added before building.");
        }

        this.provider.putValidators(this.type, this.validators);
    }

    private Field getField(String fieldName) {
        try {
            return this.type.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }
}
