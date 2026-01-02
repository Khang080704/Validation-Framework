package org.example.common;

import org.example.config.Config;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class FieldConfig {
    private Field field;
    private Set<Config> configs = new HashSet<>();

    public FieldConfig(Field field) {
        this.field = field;
        this.field.setAccessible(true);
    }

    public void addConfig(Config config) {
        this.configs.add(config);
    }

    public Field getField() {
        return this.field;
    }

    public Set<Config> getConfigs() {
        return configs;
    }
}
