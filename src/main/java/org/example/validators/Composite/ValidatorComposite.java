package org.example.validators.Composite;

import org.example.core.IValidator;
import org.example.core.IValidatorManager;
import org.example.core.ValidatorContext;
import org.example.core.ValidatorResult;

import java.util.ArrayList;
import java.util.List;

public class ValidatorComposite<T> implements IValidatorManager<T> {
    private List<IValidator<T>> validators = new ArrayList<>();

    @Override
    public void addValidator(IValidator<T> validator) {
        this.validators.add(validator);
    }


    @Override
    public ValidatorResult validate(T object) {
        for (IValidator<T> validator : this.validators) {
            ValidatorResult result = validator.validate(object);
            if (!result.isValid()) {
                return ValidatorResult.invalid(result.message());
            }
        }
        return ValidatorResult.valid();
    }

    @Override
    public void validate(ValidatorContext<T> context) {
        for (IValidator<T> validator : this.validators) {
            validator.validate(context);
        }
    }

}
