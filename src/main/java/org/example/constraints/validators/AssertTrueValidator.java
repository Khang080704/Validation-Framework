package org.example.constraints.validators;

public class AssertTrueValidator extends ConstraintValidator<Boolean> {

    @Override
    public String validate(Boolean value) {
        return (value != null && !value) ? message : null;
    }
}
