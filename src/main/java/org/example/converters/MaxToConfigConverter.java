package org.example.converters;

import org.example.config.Config;
import org.example.config.MaxConfig;
import org.example.constraints.annotation.Max;

public class MaxToConfigConverter implements AnnotationToConfigConverter<Max> {
    @Override
    public Config convert(Max annotation) {
        return new MaxConfig(annotation.message(), annotation.value());
    }

}
