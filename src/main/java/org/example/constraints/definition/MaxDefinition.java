package org.example.constraints.definition;

import org.example.config.Config;
import org.example.config.MaxConfig;

public class MaxDefinition extends ConstraintDefinition {
    private final long max;

    public MaxDefinition(long max) {
        this.max = max;
    }

    @Override
    public Config getConfig() {
        return new MaxConfig(this.message, this.max);
    }

    public long getMax() {
        return this.max;
    }
}
