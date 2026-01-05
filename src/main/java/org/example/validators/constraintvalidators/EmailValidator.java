package org.example.validators.constraintvalidators;

import org.example.config.EmailConfig;

public class EmailValidator implements ConstraintValidator<EmailConfig, String>{
    @Override
    public void initialize(EmailConfig config) {
        ConstraintValidator.super.initialize(config);
    }

    @Override
    public boolean isValid(String email) {
        if(email == null) return true;
        if(!email.contains("@")) return false;

        String[] parts = email.split("@");
        if(parts.length != 2) return false;

        if(!parts[1].contains(".")) return false;

        if(email.contains(" ")) return false;

        return true;

    }
}
