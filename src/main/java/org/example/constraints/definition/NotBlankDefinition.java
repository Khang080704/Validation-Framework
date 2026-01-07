package org.example.constraints.definition;

import org.example.constraints.annotation.NotBlank;

import java.util.Map;

public class NotBlankDefinition extends ConstraintDefinition {
    public NotBlankDefinition() {
        this.annotationType = NotBlank.class;
        this.message = "Field must not be blank";
    }

    public NotBlankDefinition message (String message) {
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
