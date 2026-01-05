package org.example.converters;

import org.example.config.Config;
import org.example.config.NotEmptyConfig;
import org.example.constraints.annotation.NotEmpty;

public class NotEmptyToConfigConverter implements AnnotationToConfigConverter<NotEmpty>{
    @Override
    public Config convert(NotEmpty annotation) {
        return new NotEmptyConfig(annotation.message());
    }
}
