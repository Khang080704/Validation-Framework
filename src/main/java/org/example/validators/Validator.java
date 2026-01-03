package org.example.validators;

import org.example.common.FieldConfig;
import org.example.common.IConstraintViolationNotifier;
import org.example.common.ValidationViolation;
import org.example.config.Config;
import org.example.configproviders.ConfigProvider;
import org.example.validators.constraintvalidators.ConstraintValidator;

import java.util.*;

public class Validator implements IValidator {
    private final ConfigProvider configProvider;
    private final IConstraintViolationNotifier notifier;

    public Validator(
        ConfigProvider configProvider,
        IConstraintViolationNotifier notifier
    ) {
        this.configProvider = configProvider;
        this.notifier = notifier;
    }

    @Override
    public ValidationViolation validate(Object object) {
        Class<?> type = object.getClass();
        Set<FieldConfig> fieldConfigs = configProvider.getConfig(type);
        ValidationViolation result = new ValidationViolation();

        try {
            for (FieldConfig fieldConfig : fieldConfigs) {
                List<String> violation = validateHelper(object, fieldConfig);

                if (!violation.isEmpty()) {
                    result.addViolation(fieldConfig.getField().getName(), violation);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public ValidationViolation validateProperty(Object object, String property) {
        ValidationViolation result = new ValidationViolation();
        Class<?> type = object.getClass();
        Set<FieldConfig> fieldConfigs = configProvider.getConfig(type);
        Optional<FieldConfig> fieldConfig = fieldConfigs.stream()
                .filter(fc -> fc.getField().getName().equals(property))
                .findFirst();

        if (fieldConfig.isPresent()) {
            List<String> violation = validateHelper(object, fieldConfig.get());
            if (!violation.isEmpty()) {
                result.addViolation(fieldConfig.get().getField().getName(), violation);
            }
        }

        return result;
    }

    private List<String> validateHelper(Object object, FieldConfig fieldConfig) {
        try {
            List<String> violation = new ArrayList<>();
            Object value = fieldConfig.getField().get(object);

            for (Config config: fieldConfig.getConfigs()) {
                ConstraintValidator validator = ValidatorRegistry.get(config.getClass()).getDeclaredConstructor().newInstance();
                if (!validator.isValid(value)) {
                    violation.add(config.message());
                }
            }

            return violation;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void notifyViolation(ValidationViolation violation) {
        if (notifier != null && violation.isViolated()) {
            notifier.display(violation);
        }
    }
}
