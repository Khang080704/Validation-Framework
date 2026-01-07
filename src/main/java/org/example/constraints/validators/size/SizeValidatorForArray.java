package org.example.constraints.validators.size;

public class SizeValidatorForArray extends SizeValidator<Object[]> {
    @Override
    public String validate(Object[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
