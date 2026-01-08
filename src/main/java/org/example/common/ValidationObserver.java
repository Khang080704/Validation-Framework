package org.example.common;

import java.util.List;

public interface ValidationObserver {
    void onValidationSuccess();
    void onValidationFailure(List<ValidationViolation> violations);
}
