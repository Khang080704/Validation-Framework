package org.example.constraints.validators.size;

public class SizeValidatorForCharArray extends SizeValidator<char[]> {
    @Override
    public String validate(char[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
