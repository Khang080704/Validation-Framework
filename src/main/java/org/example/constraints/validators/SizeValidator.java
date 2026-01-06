package org.example.constraints.validators;

import java.util.Map;

public class SizeValidator extends ConstraintValidator<String>{
    private int min;
    private int max;

    @Override
    public void initialize(Map<String, Object> attributes) {
        super.initialize(attributes);
        this.min = (Integer) attributes.get("min");
        this.max = (Integer) attributes.get("max");
    }

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
