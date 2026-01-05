package org.example.validators.constraintvalidators;

import org.example.config.MinConfig;

import java.math.BigDecimal;

public class MinValidator implements ConstraintValidator<MinConfig, Number>{
    private long min;

    @Override
    public void initialize(MinConfig config) {
        ConstraintValidator.super.initialize(config);
        this.min = config.getMin();
    }

    @Override
    public boolean isValid(Number value) {
        if(value == null) return true;

        BigDecimal v = new BigDecimal(value.toString());
        BigDecimal minValue = BigDecimal.valueOf(min);
        boolean isValid = v.compareTo(minValue) >= 0;
        return isValid;
    }
}
