package org.example.constraints.validators.size;

public class SizeValidatorForShortArray extends SizeValidator<short[]> {
    @Override
    public String validate(short[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
