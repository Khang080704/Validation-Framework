package org.example.core;

public interface IValidatorManager<T> extends IValidator<T> {
    void addValidator(IValidator<T> validator);
}
