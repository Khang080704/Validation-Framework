package org.example.builder;

import org.example.core.IValidator;
import org.example.core.IValidatorManager;
import org.example.core.ValidatorContext;
import org.example.core.ValidatorResult;
import org.example.validators.Basic.StringValidator.RequireValidator;
import org.example.validators.Composite.ValidatorComposite;

public abstract class AbstractValidatorBuilder<T, B extends AbstractValidatorBuilder<T, B>> {
    protected IValidatorManager<T> manager = new ValidatorComposite<>();

    protected abstract B self();

    public B require() {
        manager.addValidator(new RequireValidator<>());
        return self();
    }

    public B custom(IValidator<T> validator) {
        manager.addValidator(validator);
        return self();
    }

    public B build() {
        return self();
    }
    public void validate(ValidatorContext<T> value) {
        manager.validate(value);
    }
    public ValidatorResult validate(T value) {
        return manager.validate(value);
    }
}
