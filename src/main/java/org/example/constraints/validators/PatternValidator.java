package org.example.constraints.validators;

import java.util.Map;
import java.util.regex.Pattern;

public class PatternValidator extends ConstraintValidator<String>{
    private Pattern pattern;

    @Override
    public void initialize(Map<String, Object> attributes) {
        super.initialize(attributes);
        String regex = (String) attributes.get("regex");
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public String validate (String value) {
        if (value != null && pattern.matcher(value).matches()) {
            return message;
        }
        return null;
    }
}
