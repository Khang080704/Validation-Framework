package org.example.constraints.definition;

import org.example.config.Config;
import org.example.config.EmailConfig;

public class EmailDefinition extends ConstraintDefinition {
    @Override
    public Config getConfig() {
        return new EmailConfig(this.message);
    }
}
