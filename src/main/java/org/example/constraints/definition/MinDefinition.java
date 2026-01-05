package org.example.constraints.definition;

import org.example.config.Config;
import org.example.config.MinConfig;

public class MinDefinition extends ConstraintDefinition {
    private final long min;

    public MinDefinition(long min) {
        this.min = min;
    }

    @Override
    public Config getConfig() {
        return new MinConfig(message, min);
    }
    public long getMin() {
        return min;
    }
}
