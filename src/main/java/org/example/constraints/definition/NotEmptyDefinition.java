package org.example.constraints.definition;

import org.example.constraints.annotation.NotEmpty;

import java.util.Map;

public class NotEmptyDefinition extends ConstraintDefinition{
    public NotEmptyDefinition() {
        this.annotationType = NotEmpty.class;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
            "message", message
        );
    }
}
