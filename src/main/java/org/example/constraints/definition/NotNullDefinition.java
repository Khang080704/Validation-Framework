package org.example.constraints.definition;

import org.example.constraints.annotation.NotNull;

import java.util.Map;

public class NotNullDefinition extends ConstraintDefinition{
    public NotNullDefinition() {
        this.annotationType = NotNull.class;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
            "message", message
        );
    }
}
