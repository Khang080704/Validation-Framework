package org.example.annotations.core;

import org.example.annotations.Max;
import org.example.annotations.Min;
import org.example.annotations.NotNull;
import org.example.annotations.validator.MaxValidator;
import org.example.annotations.validator.MinValidator;
import org.example.annotations.validator.NotNullValidator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ValidatorRegistry {
    private static final Map<
                Class<? extends Annotation>,
                AnnotationValidator<?>
                > registry = new HashMap<>();

    static {
        register(NotNull.class, new NotNullValidator());
        register(Min.class, new MinValidator());
        register(Max.class, new MaxValidator());
    }

    public static <A extends Annotation> void register(
            Class<A> annotation,
            AnnotationValidator<A> validator) {
        registry.put(annotation, validator);
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation>
    AnnotationValidator<A> get(Class<A> annotation) {
        return (AnnotationValidator<A>) registry.get(annotation);
    }
}
