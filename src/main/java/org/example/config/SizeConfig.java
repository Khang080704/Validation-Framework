package org.example.config;

public class SizeConfig extends Config{
    private int max;
    private int min;

    public SizeConfig(String message, int min, int max) {
        super(message);
        this.max = max;
        this.min = min;
    }
    public int getMax() {
        return max;
    }
    public int getMin() {
        return min;
    }
}
