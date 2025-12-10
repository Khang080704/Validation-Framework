package org.example.core;

public interface IValidator<T> {
    ValidatorResult validate(T object);
}
