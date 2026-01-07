package org.example.constraints.validators.size;

public class SizeValidatorForBooleanArray extends SizeValidator<boolean[]> {
    @Override
    public String validate(boolean[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
