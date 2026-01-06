package org.example.constraints.definition;

import org.example.constraints.annotation.NotBlank;

import java.util.Map;

public class NotBlankDefinition extends ConstraintDefinition {
    public NotBlankDefinition() {
        this.annotationType = NotBlank.class;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
            "message", message
        );
    }
}
