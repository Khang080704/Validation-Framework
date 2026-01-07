package org.example.constraints.validators.size;

import java.util.Collection;

public class SizeValidatorForCollection extends SizeValidator<Collection<?>> {
    @Override
    public String validate(Collection<?> value) {
        if (value != null) {
            int size = value.size();
            if (size < min || size > max) {
                return message;
            }
        }
        return null;
    }
}
