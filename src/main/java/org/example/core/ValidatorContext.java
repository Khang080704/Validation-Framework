package org.example.core;

import org.example.annotations.core.AnnotationValidator;
import org.example.annotations.core.ValidatorRegistry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ValidatorContext<T> {
    private final T data;
    private final List<String> errors;

    public ValidatorContext(T data) {
        this.data = data;
        this.errors = new ArrayList<>();
    }
    public T getData() {
        return data;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void addError(String error) {
        this.errors.add(error);
    }
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public static List<ValidatorResult> validateObject(Object object) {
        List<ValidatorResult> errors = new ArrayList<>();
        Class<?> clazz = object.getClass();

        for(Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                continue;
            }

            for (Annotation annotation : field.getAnnotations()) {
                AnnotationValidator annotationValidator = ValidatorRegistry.get(annotation.annotationType());

                if(annotationValidator != null) {
                    @SuppressWarnings("unchecked")
                    ValidatorResult validatorResult = annotationValidator.validate(annotation, value, field);
                    if(!validatorResult.isValid()) {
                        errors.add(validatorResult);
                    }
                }
            }
        }

        return errors;
    }
}
