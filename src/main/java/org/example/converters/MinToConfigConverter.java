package org.example.converters;

import org.example.config.Config;
import org.example.config.MinConfig;
import org.example.constraints.annotation.Min;

public class MinToConfigConverter implements AnnotationToConfigConverter<Min> {
    @Override
    public Config convert(Min annotation) {
        return new MinConfig(annotation.message(), annotation.value());
    }
}
