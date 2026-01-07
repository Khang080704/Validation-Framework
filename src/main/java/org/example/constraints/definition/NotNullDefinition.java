package org.example.constraints.definition;

import org.example.constraints.annotation.NotNull;

import java.util.Map;

public class NotNullDefinition extends ConstraintDefinition{
    public NotNullDefinition() {
        this.annotationType = NotNull.class;
        this.message = "Field must not be null";
    }

    public NotNullDefinition message (String message) {
        this.message = message;
        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
            "message", message
        );
    }
}
