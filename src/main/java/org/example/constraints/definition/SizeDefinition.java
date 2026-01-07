package org.example.constraints.definition;

import org.example.constraints.annotation.Size;

import java.util.Map;

public class SizeDefinition extends ConstraintDefinition {
    private int min;
    private int max;

    public SizeDefinition() {
        this.annotationType = Size.class;
    }

    public SizeDefinition message (String message) {
        this.message = message;
        return this;
    }

    public SizeDefinition min(int min) {
        this.min = min;
        return this;
    }

    public SizeDefinition max(int max) {
        this.max = max;
        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
            "message", message,
            "min", min,
            "max", max
        );
    }
}
