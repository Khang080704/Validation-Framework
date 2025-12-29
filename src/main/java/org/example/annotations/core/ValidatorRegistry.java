package org.example.annotations.core;

import org.example.annotations.*;
import org.example.annotations.validator.*;
import org.example.annotations.validator.number.MaxAnnotationValidator;
import org.example.annotations.validator.number.MinAnnotationValidator;
import org.example.annotations.validator.string.EmailAnnotationValidator;
import org.example.annotations.validator.string.NotEmptyAnnotationValidator;
import org.example.annotations.validator.string.PatternAnnotationValidator;
import org.example.annotations.validator.string.SizeAnnotationValidator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ValidatorRegistry {
    private static final Map<
                Class<? extends Annotation>,
                AnnotationValidator<?>
                > registry = new HashMap<>();

    static {
        register(Email.class, new EmailAnnotationValidator());
        register(Max.class, new MaxAnnotationValidator());
        register(Min.class, new MinAnnotationValidator());
        register(NotEmpty.class, new NotEmptyAnnotationValidator());
        register(NotNull.class, new NotNullAnnotationValidator());
        register(Pattern.class, new PatternAnnotationValidator());
        register(Size.class, new SizeAnnotationValidator());
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
