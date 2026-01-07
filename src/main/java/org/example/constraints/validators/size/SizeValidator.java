package org.example.constraints.validators.size;

import org.example.constraints.validators.ConstraintValidator;

import java.util.Map;

public abstract class SizeValidator<T> extends ConstraintValidator<T> {
    protected int min;
    protected int max;

    @Override
    public void initialize(Map<String, Object> attributes) {
        super.initialize(attributes);
        this.min = (Integer) attributes.get("min");
        this.max = (Integer) attributes.get("max");
    }
}
