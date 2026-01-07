package org.example.providers;

import org.example.constraints.annotation.IsValid;
import org.example.constraints.validators.ConstraintValidator;
import org.example.constraints.validators.ConstraintValidatorRegistry;
import org.example.validators.ClassValidator;
import org.example.validators.ElementValidator;
import org.example.validators.FieldValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationClassValidatorProvider implements ClassValidatorProvider{
    @Override
    public ClassValidator getValidators(Class<?> type) {
        Set<ElementValidator> validators = new HashSet<>();

        for (Field field : type.getDeclaredFields()) {
            Set<ConstraintValidator<?>> constraintValidators = new HashSet<>();

            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType().equals(IsValid.class)) {
                    ClassValidator classValidator = getValidators(field.getType());

                    if (classValidator != null) {
                        classValidator.setField(field);
                        validators.add(classValidator);
                    }
                }
                else {
                    ConstraintValidator<?> validator = ConstraintValidatorRegistry.getInstance(annotation.annotationType(), field.getType());
                    validator.initialize(getAnnotationAttributes(annotation));
                    constraintValidators.add(validator);
                }
            }

            if (!constraintValidators.isEmpty()) {
                validators.add(new FieldValidator(field, constraintValidators));
            }
        }

        return validators.isEmpty() ? null : new ClassValidator(validators);
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
