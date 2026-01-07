package org.example.constraints.validators;

public class AssertFalseValidator extends ConstraintValidator<Boolean> {

    @Override
    public String validate(Boolean value) {
        return (value != null && value) ? message : null;
    }
}
