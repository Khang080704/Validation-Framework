# DOCUMENT: VALIDATION FRAMEWORK ARCHITECTURE & IMPLEMENTATION GUIDE
 
- Project: Validation Framework (Java)
- Team: 3 Members
- Duration: 5 Weeks

## PART 1: ARCHITECTURE OVERVIEW
Based on the requirement to utilize at least 4 Design Patterns and ensure extensibility, the system architecture is divided into the following layers and modules:

## 1. High-Level Architecture
### 1. Configuration Layer (Annotation/Fluent API):
- Defines Rules on the Object Model.

- Uses Java Annotations (@Required, @Email) or Code-based configuration.
### 2. Core Engine Layer (Creation & Parsing):
- Uses Reflection to scan Annotations from model classes.

- Uses Factory/Builder Pattern to initialize corresponding Validators from these annotations.
### 3. Validation Logic Layer (Strategies):

- Contains the actual verification algorithms.

- Applies Strategy Pattern to separate validation logic.
- Applies Composite Pattern to group multiple validators for a single field.
### 4. Notification Layer (Observer):
- Listens for validation results.
- Updates the UI (Swing/JavaFX) via the Observer Pattern.

## 2. Core Design Patterns
In the report, detailed class diagrams for the following 4 patterns are required:

| Design Pattern | Role in Framework |
|----------------|-------------------|
| Strategy       | Defines the common IValidator interface. Concrete classes (EmailValidator, RangeValidator) contain specific logic. |
| Composite| The ValidatorGroup class contains a list of List<IValidator>. Helps check multiple conditions (e.g., both Required and Email).|
| Observer | The ValidationSubject sends notifications, and IValidationObserver (UI) receives notifications to display errors immediately. |
| Factory Method | ValidatorFactory creates instances of validators based on the Annotations read from the field. |

# PART 2: DETAILED IMPLEMENTATION BY PHASE
## Phase 1: Core Framework & Strategy Pattern
- Goal: Build the basic Interface and single validator library.
- Pattern: Strategy.
```java
    // 1. Result returned from a validation attempt
public class ValidationResult {
    private boolean isValid;
    private String message;

    public ValidationResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }
    // Getters...
    public boolean isValid() { return isValid; }
    public String getMessage() { return message; }
}

// 2. Base Interface (Strategy Interface)
public interface IValidator<T> {
    ValidationResult validate(T value);
}

// 3. Concrete Strategy (Example: Check Empty)
public class RequiredValidator implements IValidator<String> {
    @Override
    public ValidationResult validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new ValidationResult(false, "This field is required.");
        }
        return new ValidationResult(true, "");
    }
}
```

## Phase 2: Grouping Validations (Composite Pattern)
- Goal: Combine multiple validations for the same data type (e.g., Password must be non-empty AND longer than 6 characters).
- Pattern: Composite.
```java
import java.util.ArrayList;
import java.util.List;

// Composite Validator: Contains a list of child validators
public class ValidatorGroup<T> implements IValidator<T> {
    private List<IValidator<T>> validators = new ArrayList<>();

    public void addValidator(IValidator<T> validator) {
        this.validators.add(validator);
    }

    @Override
    public ValidationResult validate(T value) {
        // Iterate through all child validators
        for (IValidator<T> v : validators) {
            ValidationResult result = v.validate(value);
            // Fail-fast: Return immediately upon error (or aggregate errors)
            if (!result.isValid()) {
                return result; 
            }
        }
        return new ValidationResult(true, "Success");
    }
}
```

## Phase 3: Reflection & Annotation Support
- Goal: Support automatic validation setup via data constraint declarations (Annotations).
- Pattern: Factory / Reflection.
```java
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// 1. Define Annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Rules {
    boolean required() default false;
    int minLength() default 0;
    String email() default "";
}

// 2. Validation Engine (Using Reflection)
public class ValidationContext {
    public static List<ValidationResult> validateObject(Object object) {
        List<ValidationResult> errors = new ArrayList<>();
        Class<?> clazz = object.getClass();
        
        // Scan all fields of the object
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            
            // Check if field has @Rules annotation
            if (field.isAnnotationPresent(Rules.class)) {
                Rules rule = field.getAnnotation(Rules.class);
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) { e.printStackTrace(); }

                // Factory logic: Map rule to validator
                if (rule.required()) {
                    IValidator<String> v = new RequiredValidator();
                    ValidationResult res = v.validate((String) value);
                    if (!res.isValid()) errors.add(res);
                }
                
                // Logic for other rules (MinLength, Email...)
                // ...
            }
        }
        return errors;
    }
}
```

## Phase 4: UI Integration & Observer Pattern
- Goal: Mechanism to notify the user interface when data is invalid.
- Pattern: Observer.
```java
// 1. Observer Interface (UI Component will implement this)
public interface ValidationListener {
    void onError(String fieldName, String errorMsg);
    void onSuccess(String fieldName);
}

// 2. Subject (Input Management Form)
public class FormValidator {
    private List<ValidationListener> listeners = new ArrayList<>();

    public void attach(ValidationListener listener) {
        listeners.add(listener);
    }

    private void notifyError(String field, String msg) {
        for (ValidationListener l : listeners) l.onError(field, msg);
    }

    // This method is called on Submit button click or Blur event
    public void performValidation(Object dataModel) {
        List<ValidationResult> results = ValidationContext.validateObject(dataModel);
        
        if (results.isEmpty()) {
            // Logic success
        } else {
            // Assuming ValidationResult contains the field name for mapping
            for(ValidationResult err : results) {
                // Map error to the corresponding field on UI
                notifyError("fieldNameHere", err.getMessage());
            }
        }
    }
}
```

## Phase 5: Demo & Custom Validator (Extension)
- Goal: Create Custom Validations easily and build a demo app.

- Tasks:
  - Create a User class with annotated fields.
  - Create a Java Swing/JavaFX interface.
  - Experiment with creating a PasswordMatchValidator (checking if re-entered password matches).

# PART 3: REPORT & SUBMISSION REQUIREMENTS
According to the "General Regulations," the team needs to prepare the report content in parallel with coding.
## 1. Submission Folder Structure
Create this folder structure in advance to avoid last-minute errors:
- FrameWork_name:
    - 1.Documents: Contains Report (.doc/.pdf), Presentation Slides.
    - 2.Source code: Contains the full Java project (Clean build).
    - 3.Functions List: Feature list and completion status table.
    - 4.Others: setup.exe file (if any), Demo Video.
## 2. Mandatory Report Content
The report must include the following sections:
### 1. Class Diagram: For the entire framework.
### 2. Design Pattern Explanation (Important - Minimum 4 patterns):
   - Pattern Name.
   - Class diagram corresponding to each pattern (Extract from the general diagram).
   - Code snippets using the pattern (Copy code from Phases 1-4).
   - Meaning/Significance of using the pattern (e.g., Why use Strategy for Validator? -> To easily extend new rules without modifying old code).

