package org.example.constraints.definition;

import org.example.config.Config;

public abstract class ConstraintDefinition {
    protected String message;

    public ConstraintDefinition message(String message) {
        this.message = message;
        return this;
    }

    public abstract Config getConfig();
}
