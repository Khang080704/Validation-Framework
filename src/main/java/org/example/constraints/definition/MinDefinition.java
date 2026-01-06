package org.example.constraints.definition;

import org.example.constraints.annotation.Min;

import java.util.Map;

public class MinDefinition extends ConstraintDefinition {
    private long value;

    public MinDefinition() {
        this.annotationType = Min.class;
    }

    public MinDefinition value(long value) {
        this.value = value;
        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
            "message", message,
            "value", value
        );
    }
}
