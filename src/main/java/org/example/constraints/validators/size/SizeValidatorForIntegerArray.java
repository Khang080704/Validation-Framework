package org.example.constraints.validators.size;

public class SizeValidatorForIntegerArray extends SizeValidator<int[]> {
    @Override
    public String validate(int[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
