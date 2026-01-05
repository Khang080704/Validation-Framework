package org.example.constraints.definition;

import org.example.config.Config;
import org.example.config.PatternConfig;

public class PatternDefinition extends ConstraintDefinition{
    private final String regex;
    public PatternDefinition(String regex) {
        this.regex = regex;
    }

    @Override
    public Config getConfig() {
        return new PatternConfig(this.message, this.regex);
    }

    public String getRegex() {
        return regex;
    }
}
