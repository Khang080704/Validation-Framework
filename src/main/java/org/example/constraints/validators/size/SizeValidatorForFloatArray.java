package org.example.constraints.validators.size;

public class SizeValidatorForFloatArray extends SizeValidator<float[]> {
    @Override
    public String validate(float[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
