package org.example.core;

import java.util.ArrayList;
import java.util.List;

public class ValidatorContext<T> {
    private final T data;
    private final List<String> errors;

    public ValidatorContext(T data) {
        this.data = data;
        this.errors = new ArrayList<>();
    }
    public T getData() {
        return data;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void addError(String error) {
        this.errors.add(error);
    }
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
