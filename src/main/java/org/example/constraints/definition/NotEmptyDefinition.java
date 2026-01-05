package org.example.constraints.definition;

import org.example.config.Config;
import org.example.config.NotEmptyConfig;

public class NotEmptyDefinition extends ConstraintDefinition{
    @Override
    public Config getConfig() {
        return new NotEmptyConfig(this.message);
    }
}
