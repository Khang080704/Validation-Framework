package org.example.converters;

import org.example.constraints.annotation.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class AnnotationToConfigConverterRegistry {
    private static Map<Class<? extends Annotation>, AnnotationToConfigConverter<? extends Annotation>> registry = new HashMap<>();

    static {
        register(NotNull.class, new NotNullToConfigConverter());
        register(Email.class, new EmailToConfigConverter());
        register(Max.class, new MaxToConfigConverter());
        register(Min.class, new MinToConfigConverter());
        register(NotEmpty.class, new NotEmptyToConfigConverter());
        register(Pattern.class, new PatternToConfigConverter());
        register(Size.class, new SizeToConfigConverter());
    }

    public static <A extends Annotation> void register(Class<? extends Annotation> annotation, AnnotationToConfigConverter<A> converter) {
        registry.put(annotation, converter);
    }

    public static AnnotationToConfigConverter<? extends Annotation> getConverter(Class<? extends Annotation> annotation) {
        return registry.get(annotation);
    }
}
