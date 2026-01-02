package org.example.configproviders;

import org.example.common.FieldConfig;

import java.util.Set;

public interface ConfigProvider {
    Set<FieldConfig> getConfig(Class<?> type);
}
