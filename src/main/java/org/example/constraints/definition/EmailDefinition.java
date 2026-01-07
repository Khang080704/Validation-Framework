package org.example.constraints.definition;

import org.example.constraints.annotation.Email;

import java.util.Map;

public class EmailDefinition extends ConstraintDefinition {
    public EmailDefinition() {
        this.annotationType = Email.class;
    }

    public EmailDefinition message(String message) {
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
