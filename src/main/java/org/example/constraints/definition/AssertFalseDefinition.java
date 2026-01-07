package org.example.constraints.definition;

import org.example.constraints.annotation.AssertFalse;

import java.util.Map;

public class AssertFalseDefinition extends ConstraintDefinition {
    public AssertFalseDefinition() {
        this.annotationType = AssertFalse.class;
        this.message = "Field must be false";
    }

    public AssertFalseDefinition message(String message) {
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
