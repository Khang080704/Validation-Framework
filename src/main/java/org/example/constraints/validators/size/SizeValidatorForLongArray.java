package org.example.constraints.validators.size;

public class SizeValidatorForLongArray extends SizeValidator<long[]> {
    @Override
    public String validate(long[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
