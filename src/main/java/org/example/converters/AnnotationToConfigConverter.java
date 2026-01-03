package org.example.converters;

import org.example.config.Config;

import java.lang.annotation.Annotation;

public interface AnnotationToConfigConverter<T extends Annotation> {
    Config convert(T annotation);
}
