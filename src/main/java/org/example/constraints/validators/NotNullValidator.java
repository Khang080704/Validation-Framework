package org.example.constraints.validators;

public class NotNullValidator extends ConstraintValidator<Object> {

    @Override
    public String validate(Object value) {
        return value == null ? message : null;
    }
}
