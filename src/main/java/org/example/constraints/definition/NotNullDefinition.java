package org.example.constraints.definition;

import org.example.config.Config;
import org.example.config.NotNullConfig;

public class NotNullDefinition extends ConstraintDefinition{

    @Override
    public Config getConfig() {
        return new NotNullConfig(this.message);
    }
}
