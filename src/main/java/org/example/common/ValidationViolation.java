package org.example.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationViolation {
    private final Map<String, List<String>> violations = new HashMap<>();

    public Map<String, List<String>> getViolations() {
        return violations;
    }

    public void addViolation(String field, List<String> messages) {
        violations.put(field, messages);
    }

    public boolean isViolated() {
        return !violations.isEmpty();
    }
}
