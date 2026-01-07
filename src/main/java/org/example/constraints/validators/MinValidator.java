package org.example.constraints.validators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class MinValidator extends ConstraintValidator<Number>{
    private long value;

    @Override
    public void initialize(Map<String, Object> attributes) {
        super.initialize(attributes);
        this.value = (Long) attributes.get("value");
    }

    @Override
    public String validate(Number number) {
        boolean isValid = true;

        if (number != null) {
            if (number instanceof Double) {
                isValid = Double.compare((Double) number, this.value) >= 0;
            } else if (number instanceof Float) {
                isValid = Float.compare((Float) number, this.value) >= 0;
            } else if (number instanceof BigDecimal) {
                isValid = ((BigDecimal) number).compareTo(BigDecimal.valueOf(this.value)) >= 0;
            } else if (number instanceof BigInteger) {
                isValid = ((BigInteger) number).compareTo(BigInteger.valueOf(this.value)) >= 0;
            } else if (number instanceof Byte || number instanceof Short || number instanceof Integer || number instanceof Long) {
                isValid = number.longValue() >= this.value;
            }
        }

        return isValid ? null : message;
    }
}
