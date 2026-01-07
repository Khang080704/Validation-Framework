package org.example.constraints.definition;

import org.example.constraints.annotation.AssertTrue;

import java.util.Map;

public class AssertTrueDefinition extends ConstraintDefinition {
    public AssertTrueDefinition() {
        this.annotationType = AssertTrue.class;
        this.message = "Field must be true";
    }

    public AssertTrueDefinition message(String message) {
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
