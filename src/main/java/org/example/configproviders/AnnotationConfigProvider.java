package org.example.configproviders;

import org.example.common.FieldConfig;
import org.example.config.Config;
import org.example.converters.AnnotationToConfigConverter;
import org.example.converters.AnnotationToConfigConverterRegistry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class AnnotationConfigProvider implements ConfigProvider {

    @Override
    public Set<FieldConfig> getConfig(Class<?> type) {
        Set<FieldConfig> fieldConfigs = new HashSet<>();

        for (Field field : type.getDeclaredFields()) {
            FieldConfig fieldConfig = new FieldConfig(field);

            for (Annotation annotation : field.getAnnotations()) {
                AnnotationToConfigConverter converter = AnnotationToConfigConverterRegistry.getConverter(annotation.annotationType());
                Config config = converter.convert(annotation);
                fieldConfig.addConfig(config);
            }

            if (!fieldConfig.getConfigs().isEmpty()) {
                fieldConfigs.add(fieldConfig);
            }
        }

        return fieldConfigs;
    }
}
