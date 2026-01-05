package org.example.converters;

import org.example.config.Config;
import org.example.config.EmailConfig;
import org.example.constraints.annotation.Email;

public class EmailToConfigConverter implements AnnotationToConfigConverter<Email>{
    @Override
    public Config convert(Email annotation) {
        return new EmailConfig(annotation.message());
    }
}
