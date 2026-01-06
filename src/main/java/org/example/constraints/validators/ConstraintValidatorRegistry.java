package org.example.constraints.validators;

import org.example.constraints.annotation.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ConstraintValidatorRegistry {
    private static Map<Class<? extends Annotation>, Map<Class<?>, Class<? extends ConstraintValidator<?>>>> registry = new HashMap<>();

    static {
        register(Email.class, String.class, EmailValidator.class);
        register(Max.class, Number.class, MaxValidator.class);
        register(Min.class, Number.class, MinValidator.class);
        register(NotBlank.class, String.class, NotBlankValidator.class);
        register(NotEmpty.class, String.class, NotEmptyValidator.class);
        register(NotNull.class, Object.class, NotNullValidator.class);
        register(Pattern.class, String.class, PatternValidator.class);
        register(Size.class, String.class, SizeValidator.class);
    }

    public static ConstraintValidator<?> getInstance(Class<? extends Annotation> annotationType, Class<?> fieldType) {
        if (registry.containsKey(annotationType)) {
            Map<Class<?>, Class<? extends ConstraintValidator<?>>> validators = registry.get(annotationType);

            if (validators.containsKey(fieldType)) {
                return createInstance(validators.get(fieldType));
            }

            for (Map.Entry<Class<?>, Class<? extends ConstraintValidator<?>>> entry : validators.entrySet()) {
                Class<?> registeredType = entry.getKey();
                if (registeredType.isAssignableFrom(fieldType)) {
                    return createInstance(entry.getValue());
                }
            }

            Class<?> wrapperType = getWrapperType(fieldType);
            if (wrapperType != null && validators.containsKey(wrapperType)) {
                return createInstance(validators.get(wrapperType));
            }
        }

        throw new RuntimeException("No validator found for annotation: " + annotationType.getName() + " and field type: " + fieldType.getName());
    }

    public static void register(Class<? extends Annotation> annotationType, Class<?> fieldType, Class<? extends ConstraintValidator<?>> validatorClass) {
        registry.computeIfAbsent(annotationType, k -> new HashMap<>()).put(fieldType, validatorClass);
    }

    public static void unregister(Class<? extends Annotation> annotationType, Class<?> fieldType) {
        if (registry.containsKey(annotationType)) {
            registry.get(annotationType).remove(fieldType);
        }
    }

    private static ConstraintValidator<?> createInstance(Class<? extends ConstraintValidator<?>> validatorClass) {
        try {
            return validatorClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate validator: " + validatorClass.getName(), e);
        }
    }

    private static Class<?> getWrapperType(Class<?> primitiveType) {
        return switch (primitiveType.getName()) {
            case "int" -> Integer.class;
            case "long" -> Long.class;
            case "double" -> Double.class;
            case "float" -> Float.class;
            case "boolean" -> Boolean.class;
            case "byte" -> Byte.class;
            case "short" -> Short.class;
            case "char" -> Character.class;
            default -> null;
        };
    }
}
