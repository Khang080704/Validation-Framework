package org.example.configproviders;

import org.example.common.FieldConfig;

import java.util.*;

public class CachedConfigProvider implements ConfigProvider {
    private final Map<Class<?>, Set<FieldConfig>> cache = new HashMap<>();
    private final List<ConfigProvider> providers;

    public CachedConfigProvider(List<ConfigProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Set<FieldConfig> getConfig(Class<?> type) {
        return cache.computeIfAbsent(type, key -> {
            Set<FieldConfig> combinedConfigs = new HashSet<>();

            for (ConfigProvider provider : providers) {
                Set<FieldConfig> configs = provider.getConfig(type);

                if (configs != null) {
                    combinedConfigs.addAll(configs);
                }
            }

            return combinedConfigs;
        });
    }
}
