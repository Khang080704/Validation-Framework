package org.example.validators;

import org.example.config.*;
import org.example.validators.constraintvalidators.*;

import java.util.HashMap;
import java.util.Map;

public class ValidatorRegistry {
    private static Map<Class<? extends Config>, Class<? extends ConstraintValidator<? extends Config, ?>>> registry = new HashMap<>();

    static {
        register(NotNullConfig.class, NotNullValidator.class);
        register(EmailConfig.class, EmailValidator.class);
        register(MaxConfig.class, MaxValidator.class);
        register(MinConfig.class, MinValidator.class);
        register(NotEmptyConfig.class, NotEmptyValidator.class);
        register(PatternConfig.class, PatternValidator.class);
        register(SizeConfig.class, SizeValidator.class);
    }

    public static void register(Class<? extends Config> config, Class<? extends ConstraintValidator<? extends Config, ?>> validator) {
        registry.put(config, validator);
    }

    public static Class<? extends ConstraintValidator<? extends Config, ?>> get(Class<? extends Config> annotation) {
        return registry.get(annotation);
    }
}
