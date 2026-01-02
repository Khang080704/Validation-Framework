package org.example.validators;

import org.example.common.FieldConfig;
import org.example.config.Config;
import org.example.configproviders.ConfigProvider;
import org.example.validators.constraintvalidators.ConstraintValidator;

import java.lang.reflect.Field;
import java.util.*;

public class Validator implements IValidator {
    private final ConfigProvider configProvider;

    public Validator(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @Override
    public Map<String, List<String>> validate(Object object) {
        Class<?> type = object.getClass();
        Set<FieldConfig> fieldConfigs = configProvider.getConfig(type);
        Map<String, List<String>> violations = new HashMap<>();

        try {
            for (FieldConfig fieldConfig : fieldConfigs) {
                List<String> violation = new ArrayList<>();
                Object value = fieldConfig.getField().get(object);

                for (Config config: fieldConfig.getConfigs()) {
                    ConstraintValidator validator = ValidatorRegistry.get(config.getClass()).getDeclaredConstructor().newInstance();
                    if (!validator.isValid(value)) {
                        violation.add(config.message());
                    }
                }

                if (!violation.isEmpty()) {
                    violations.put(fieldConfig.getField().getName(), violation);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return violations;
    }
}
