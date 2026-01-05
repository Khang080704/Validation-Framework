package org.example.converters;

import org.example.config.Config;
import org.example.config.SizeConfig;
import org.example.constraints.annotation.Size;

public class SizeToConfigConverter implements AnnotationToConfigConverter<Size> {
    @Override
    public Config convert(Size annotation) {
        return new SizeConfig(annotation.message(), annotation.min(), annotation.max());
    }
}
