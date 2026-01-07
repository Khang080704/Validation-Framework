package org.example.constraints.definition;

import org.example.constraints.annotation.IsValid;

import java.util.Map;

public class IsValidDefinition extends ConstraintDefinition {
    public IsValidDefinition() {
        this.annotationType = IsValid.class;
    }

    public IsValidDefinition message(String message) {
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
