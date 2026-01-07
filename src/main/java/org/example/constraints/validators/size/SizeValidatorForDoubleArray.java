package org.example.constraints.validators.size;

public class SizeValidatorForDoubleArray extends SizeValidator<double[]> {
    @Override
    public String validate(double[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
