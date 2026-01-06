package org.example.constraints.definition;

import java.lang.annotation.Annotation;
import java.util.Map;

public abstract class ConstraintDefinition {
    protected String message;
    protected Class<? extends Annotation> annotationType;

    public ConstraintDefinition message(String message) {
        this.message = message;
        return this;
    }

    public Class<? extends Annotation> getAnnotationType() {
        return this.annotationType;
    }

    public abstract Map<String, Object> getAttributes();
}
