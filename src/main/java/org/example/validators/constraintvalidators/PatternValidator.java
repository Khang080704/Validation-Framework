package org.example.validators.constraintvalidators;

import org.example.config.PatternConfig;

public class PatternValidator implements ConstraintValidator<PatternConfig, String>{
    private String regex;

    @Override
    public void initialize(PatternConfig config) {
        ConstraintValidator.super.initialize(config);
        this.regex = config.getRegex();
    }

    @Override
    public boolean isValid(String value) {
        if(value == null) return true;

        return value.matches(regex);
    }
}
