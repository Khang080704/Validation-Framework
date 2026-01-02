package org.example.validators;

import org.example.config.Config;
import org.example.config.NotNullConfig;
import org.example.constraints.NotNull;
import org.example.validators.constraintvalidators.ConstraintValidator;
import org.example.validators.constraintvalidators.NotNullValidator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ValidatorRegistry {
    private static Map<Class<? extends Config>, Class<? extends ConstraintValidator<? extends Config, ?>>> registry = new HashMap<>();

    static {
        register(NotNullConfig.class, NotNullValidator.class);
    }

    public static void register(Class<? extends Config> config, Class<? extends ConstraintValidator<? extends Config, ?>> validator) {
        registry.put(config, validator);
    }

    public static Class<? extends ConstraintValidator<? extends Config, ?>> get(Class<? extends Config> annotation) {
        return registry.get(annotation);
    }
}
