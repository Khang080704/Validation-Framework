package org.example.constraints.validators.size;

public class SizeValidatorForString extends SizeValidator<String>{
    @Override
    public String validate(String value) {
        if (value != null) {
            int length = value.length();
            if (length < min || length > max) {
                return message;
            }
        }
        return null;
    }
}
