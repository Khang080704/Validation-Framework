package org.example.constraints.definition;

import org.example.constraints.annotation.Pattern;

import java.util.Map;

public class PatternDefinition extends ConstraintDefinition{
    private String regex;

    public PatternDefinition() {
        this.annotationType = Pattern.class;
    }

    public PatternDefinition message (String message) {
        this.message = message;
        return this;
    }

    public PatternDefinition regex(String regex) {
        this.regex = regex;
        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
            "message", message,
            "regex", regex
        );
    }
}
