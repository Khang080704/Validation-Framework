package org.example.converters;

import org.example.config.Config;
import org.example.config.PatternConfig;
import org.example.constraints.annotation.Pattern;

public class PatternToConfigConverter implements AnnotationToConfigConverter<Pattern>{
    @Override
    public Config convert(Pattern annotation) {
        return new PatternConfig(annotation.message(), annotation.regex());
    }
}
