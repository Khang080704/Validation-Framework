package org.example.constraints.validators;

import java.util.regex.Pattern;

public class EmailValidator extends ConstraintValidator<String>{
    private final String REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$";
    private final Pattern PATTERN = Pattern.compile(REGEX);

    @Override
    public String validate(String value) {
        if (value != null && !value.isEmpty()) {
            if (!PATTERN.matcher(value).matches()) {
                return message;
            }
        }

        return null;
    }
}
