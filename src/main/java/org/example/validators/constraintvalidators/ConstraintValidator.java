package org.example.validators.constraintvalidators;

import org.example.config.Config;

public interface ConstraintValidator<C extends Config, T> {
    default void initialize(C config) {}
    boolean isValid(T value);
}
