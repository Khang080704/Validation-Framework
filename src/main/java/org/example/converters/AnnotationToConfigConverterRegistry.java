package org.example.converters;

import org.example.constraints.annotation.NotNull;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class AnnotationToConfigConverterRegistry {
    private static Map<Class<? extends Annotation>, AnnotationToConfigConverter<? extends Annotation>> registry = new HashMap<>();

    static {
        register(NotNull.class, new NotNullToConfigConverter());
    }

    public static <A extends Annotation> void register(Class<? extends Annotation> annotation, AnnotationToConfigConverter<A> converter) {
        registry.put(annotation, converter);
    }

    public static AnnotationToConfigConverter<? extends Annotation> getConverter(Class<? extends Annotation> annotation) {
        return registry.get(annotation);
    }
}
