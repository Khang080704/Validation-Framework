package org.example.constraints.definition;

import java.lang.annotation.Annotation;
import java.util.Map;

public abstract class ConstraintDefinition {
    protected String message;
    protected Class<? extends Annotation> annotationType;

    public Class<? extends Annotation> getAnnotationType() {
        return this.annotationType;
    }

    public abstract Map<String, Object> getAttributes();
}
