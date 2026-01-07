package org.example.constraints.validators.size;

public class SizeValidatorForByteArray extends SizeValidator<byte[]> {
    @Override
    public String validate(byte[] value) {
        if (value != null) {
            int length = value.length;
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
