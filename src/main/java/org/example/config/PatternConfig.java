package org.example.config;

public class PatternConfig extends Config{
    private final String regex;
    public PatternConfig(String message, String regex) {
        super(message);
        this.regex = regex;
    }
    public String getRegex() {
        return regex;
    }
}
