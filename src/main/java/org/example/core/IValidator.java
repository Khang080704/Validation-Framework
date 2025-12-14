package org.example.core;

public interface IValidator<T> {
    ValidatorResult validate(T object);

    default void validate(ValidatorContext<T> context) {
        ValidatorResult result = validate(context.getData());

        if(!result.isValid()) {
            context.addError(result.message());
        }
    }
}
