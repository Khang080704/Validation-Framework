package org.example.config;

public class MinConfig extends Config{
    private final long min;
    public MinConfig(String message, long minValue) {
        super(message);
        this.min = minValue;
    }
    public long getMin() {
        return min;
    }
}
