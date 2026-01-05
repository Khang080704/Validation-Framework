package org.example.config;

public class MaxConfig extends Config{
    private final long value;
    public MaxConfig(String message, long value) {
        super(message);
        this.value = value;
    }
    public long getValue() {
        return this.value;
    }
}
