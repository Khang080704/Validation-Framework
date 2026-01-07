package org.example.constraintbuilder;

import org.example.constraints.definition.ConstraintDefinition;
import org.example.providers.ProgrammaticClassValidatorProvider;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConstraintBuilder {
    private Class<?> type;
    private final Map<Field, Set<ConstraintDefinition>> constraints = new HashMap<>();
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

        this.constraints.put(field, Set.of(defs));
        return this;
    }

    public void build() {
        if (this.type == null) {
            throw new IllegalStateException("Type must be set before building configurations.");
        }

        if (this.constraints.isEmpty()) {
            throw new IllegalStateException("At least one field configuration must be added before building.");
        }

        this.provider.createValidators(this.type, this.constraints);
    }

    private Field getField(String fieldName) {
        try {
            return this.type.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }
}
