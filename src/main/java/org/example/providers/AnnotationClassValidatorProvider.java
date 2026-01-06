package org.example.providers;

import org.example.common.FieldValidator;
import org.example.constraints.validators.ConstraintValidator;
import org.example.constraints.validators.ConstraintValidatorRegistry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationClassValidatorProvider implements ClassValidatorProvider{
    @Override
    public Set<FieldValidator> getValidators(Class<?> type) {
        Set<FieldValidator> validators = new HashSet<>();

        for (Field field : type.getDeclaredFields()) {
            Set<ConstraintValidator<?>> constraintValidators = new HashSet<>();

            for (Annotation annotation : field.getAnnotations()) {
                ConstraintValidator<?> validator = ConstraintValidatorRegistry.getInstance(annotation.getClass(), field.getClass());
                validator.initialize(getAnnotationAttributes(annotation));
                constraintValidators.add(validator);
            }

            validators.add(new FieldValidator(field, constraintValidators));
        }

        return validators;
    }

    private Map<String, Object> getAnnotationAttributes(Annotation annotation) {
        Map<String, Object> attributes = new HashMap<>();

        try {
            for (Method method : annotation.annotationType().getDeclaredMethods()) {
                Object value = method.invoke(annotation);
                attributes.put(method.getName(), value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return attributes;
    }
}
