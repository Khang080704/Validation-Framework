package org.example.constraints.validators;

public class NotBlankValidator extends ConstraintValidator<String> {
    @Override
    public String validate(String value) {
        return (value == null || value.isBlank()) ? message : null;
    }
}
