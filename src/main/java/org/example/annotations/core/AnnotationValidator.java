package org.example.annotations.core;

import org.example.core.ValidatorResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface AnnotationValidator<A extends Annotation> {
    ValidatorResult validate(A annotation, Object value, Field field);
}
