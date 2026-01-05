package org.example.validators.constraintvalidators;

import org.example.config.MaxConfig;

import java.math.BigDecimal;

public class MaxValidator implements ConstraintValidator<MaxConfig, Number>{
    private long max;


    @Override
    public void initialize(MaxConfig config) {
        ConstraintValidator.super.initialize(config);
        this.max = config.getValue();
    }

    @Override
    public boolean isValid(Number value) {
        if(value == null) return true;

        BigDecimal v = new BigDecimal(value.toString());
        BigDecimal maxValue = BigDecimal.valueOf(max);
        boolean isValid = v.compareTo(maxValue) <= 0;
        return isValid;
    }

}
