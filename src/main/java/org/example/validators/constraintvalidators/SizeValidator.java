package org.example.validators.constraintvalidators;

import org.example.config.SizeConfig;
import org.example.constraints.annotation.Size;

public class SizeValidator implements ConstraintValidator<SizeConfig, String>{
    private int min;
    private int max;

    @Override
    public void initialize(SizeConfig config) {
        ConstraintValidator.super.initialize(config);
        this.min = config.getMin();
        this.max = config.getMax();
    }

    @Override
    public boolean isValid(String value) {
        if(value == null) return true;

        return value.length() <= min && value.length() <= max;
    }
}
