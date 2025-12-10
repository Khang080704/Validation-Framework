package org.example.core;

public record ValidatorResult(boolean isValid, String message) {

    public ValidatorResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public static ValidatorResult valid() {
        return new ValidatorResult(true, null);
    }
    public static ValidatorResult invalid(String message) {
        return new ValidatorResult(false, message);
    }

}
