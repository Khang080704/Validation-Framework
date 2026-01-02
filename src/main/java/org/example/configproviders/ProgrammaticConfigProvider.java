package org.example.configproviders;

import org.example.common.FieldConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProgrammaticConfigProvider implements ConfigProvider {
    private Map<Class<?>, Set<FieldConfig>> configs = new HashMap<>();

    @Override
    public Set<FieldConfig> getConfig(Class<?> type) {
        Set<FieldConfig> fieldConfigs = configs.get(type);
        return fieldConfigs != null ? fieldConfigs : Set.of();
    }

    public void putConfigs(Class<?> type, Set<FieldConfig> fieldConfigs) {
        configs.put(type, fieldConfigs);
    }
}
