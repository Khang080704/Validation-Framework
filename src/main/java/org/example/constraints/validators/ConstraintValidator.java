package org.example.constraints.validators;

import java.util.Map;

public abstract class ConstraintValidator<T> {
    protected String message;

    public void initialize(Map<String, Object> attributes) {
        this.message = (String) attributes.get("message");
    }

    public abstract String validate(T value);
}
