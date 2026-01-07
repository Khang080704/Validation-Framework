package org.example.constraints.validators.size;

import java.util.Map;

public class SizeValidatorForMap extends SizeValidator<Map<?, ?>> {
    @Override
    public String validate(Map<?, ?> value) {
        if (value != null) {
            int size = value.size();
            if (size < min || size > max) {
                return message;
            }
        }
        return null;
    }
}
