package org.example.constraints.validators;

public class NotEmptyValidator extends ConstraintValidator<String>{

    @Override
    public String validate(String value) {
        return (value == null || value.isEmpty()) ? message : null;
    }
}
