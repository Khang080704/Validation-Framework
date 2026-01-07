package org.example.constraints.definition;

import org.example.constraints.annotation.NotEmpty;

import java.util.Map;

public class NotEmptyDefinition extends ConstraintDefinition{
    public NotEmptyDefinition() {
        this.annotationType = NotEmpty.class;
        this.message = "Field must not be empty";
    }

    public NotEmptyDefinition message (String message) {
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
