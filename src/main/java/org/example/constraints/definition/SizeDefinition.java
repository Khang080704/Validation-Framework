package org.example.constraints.definition;

import org.example.config.Config;
import org.example.config.SizeConfig;

public class SizeDefinition extends ConstraintDefinition {
    private int min;
    private int max;

    public SizeDefinition(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Config getConfig() {
        return new SizeConfig(message, min, max);
    }

    public int getMax() {
        return max;
    }
    public int getMin() {
        return min;
    }
}
