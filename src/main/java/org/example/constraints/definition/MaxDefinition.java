package org.example.constraints.definition;

import org.example.constraints.annotation.Max;

import java.util.Map;

public class MaxDefinition extends ConstraintDefinition {
    private long value;

    public MaxDefinition() {
        this.annotationType = Max.class;
    }

    public MaxDefinition message(String message) {
        this.message = message;
        return this;
    }

    public MaxDefinition value(long value) {
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
