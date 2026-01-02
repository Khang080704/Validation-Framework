package org.example.converters;

import org.example.config.Config;
import org.example.config.NotNullConfig;
import org.example.constraints.NotNull;

import java.lang.annotation.Annotation;

public class NotNullToConfigConverter implements AnnotationToConfigConverter<NotNull>{

    @Override
    public Config convert(NotNull annotation) {
        return new NotNullConfig(annotation.message());
    }
}
