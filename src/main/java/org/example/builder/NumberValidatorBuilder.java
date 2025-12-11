package org.example.builder;

import org.example.validators.Basic.NumberValidator.MaxValidator;
import org.example.validators.Basic.NumberValidator.MinValidator;
import org.example.validators.Basic.NumberValidator.RangeValidator;

public class NumberValidatorBuilder<T extends Number & Comparable<T>> extends AbstractValidatorBuilder<T, NumberValidatorBuilder<T>>{
    private NumberValidatorBuilder() {
        super();
    }

    @Override
    protected NumberValidatorBuilder<T> self() {
        return this;
    }

    public static <N extends Number & Comparable<N>> NumberValidatorBuilder<N> builder() {
        return new NumberValidatorBuilder<>();
    }

    public NumberValidatorBuilder<T> min(T min) {
        manager.addValidator(new MinValidator<>(min));
        return this;
    }
    public NumberValidatorBuilder<T> max(T max) {
        manager.addValidator(new MaxValidator<>(max));
        return this;
    }
    public NumberValidatorBuilder<T> range(T min, T max) {
        manager.addValidator(new RangeValidator<>(min, max));
        return this;
    }
}
