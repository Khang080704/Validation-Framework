package org.example.constraintbuilder;

import org.example.common.FieldConfig;
import org.example.config.Config;
import org.example.configproviders.ProgrammaticConfigProvider;
import org.example.constraints.definition.ConstraintDefinition;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ConstraintBuilder {
    private Class<?> type;
    private final Set<FieldConfig> fieldConfigs = new HashSet<>();
    private final ProgrammaticConfigProvider configProvider;

    public ConstraintBuilder(ProgrammaticConfigProvider configProvider) {
        this.configProvider = configProvider;
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

        FieldConfig fieldConfig = new FieldConfig(field);

        for (ConstraintDefinition def : defs) {
            fieldConfig.addConfig(def.getConfig());
        }

        this.fieldConfigs.add(fieldConfig);
        return this;
    }

    public void build() {
        if (this.type == null) {
            throw new IllegalStateException("Type must be set before building configurations.");
        }

        if (this.fieldConfigs.isEmpty()) {
            throw new IllegalStateException("At least one field configuration must be added before building.");
        }

        this.configProvider.putConfigs(this.type, this.fieldConfigs);
    }

    private Field getField(String fieldName) {
        try {
            return this.type.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }
}
