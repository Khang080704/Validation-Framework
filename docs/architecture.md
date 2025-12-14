# DOCUMENT: VALIDATION FRAMEWORK ARCHITECTURE & IMPLEMENTATION GUIDE
 
- Project: Validation Framework (Java)
- Team: 3 Members
- Duration: 5 Weeks

## PART 1: ARCHITECTURE OVERVIEW
Based on the requirement to utilize at least 3 Design Patterns and ensure extensibility, the system architecture is divided into the following layers and modules:

## 1. High-Level Architecture
### 1. Configuration Layer (Fluent API/Builder):
- Defines Rules on the data using Builder Pattern with method chaining.

- Supports String and Number validations through specific builders.
### 2. Core Engine Layer (Validation Logic):
- Uses Strategy Pattern to separate validation algorithms.

- Uses Composite Pattern to group multiple validators for a single field.
### 3. Validation Execution Layer:
- ValidatorContext manages validation state and error collection.

- IValidator interface defines the validation contract.

## 2. Core Design Patterns
In the report, detailed class diagrams for the following 3 patterns are required:

| Design Pattern | Role in Framework |
|----------------|-------------------|
| Strategy       | Defines the common IValidator interface. Concrete classes (EmailValidator, RangeValidator) contain specific logic. |
| Composite      | The ValidatorComposite class contains a list of List<IValidator>. Helps check multiple conditions (e.g., both Required and Email).|
| Builder        | AbstractValidatorBuilder and concrete builders (StringValidatorBuilder, NumberValidatorBuilder) facilitate fluent API for validator construction. |

# PART 2: DETAILED IMPLEMENTATION BY PHASE
## Phase 1: Core Framework & Strategy Pattern
- Goal: Build the basic Interface and single validator library.
- Pattern: Strategy.
```java
    // 1. Result returned from a validation attempt
public record ValidatorResult(boolean isValid, String message) {

    public static ValidatorResult valid() {
        return new ValidatorResult(true, null);
    }
    public static ValidatorResult invalid(String message) {
        return new ValidatorResult(false, message);
    }
}

// 2. Base Interface (Strategy Interface)
public interface IValidator<T> {
    ValidatorResult validate(T value);

    default void validate(ValidatorContext<T> context) {
        ValidatorResult result = validate(context.getData());

        if(!result.isValid()) {
            context.addError(result.message());
        }
    }
}

// 3. Concrete Strategy (Example: Email Check)
public class EmailValidator implements IValidator<String> {
    @Override
    public ValidatorResult validate(String email) {
        if (!email.contains("@"))
            return ValidatorResult.invalid(email + " must contain @");

        String[] parts = email.split("@");
        if (parts.length != 2)
            return ValidatorResult.invalid(email + " must contain only one @");

        if (!parts[1].contains("."))
            return ValidatorResult.invalid(email + " must contain .");

        if (email.contains(" "))
            return ValidatorResult.invalid(email + " must not contain whitespace");

        return ValidatorResult.valid();
    }
}
```

## Phase 2: Grouping Validations (Composite Pattern)
- Goal: Combine multiple validations for the same data type (e.g., Password must be non-empty AND longer than 6 characters).
- Pattern: Composite.
```java
import java.util.ArrayList;
import java.util.List;

public interface IValidatorManager<T> extends IValidator<T> {
    void addValidator(IValidator<T> validator);
}

// Composite Validator: Contains a list of child validators
public class ValidatorComposite<T> implements IValidatorManager<T> {
    private List<IValidator<T>> validators = new ArrayList<>();

    @Override
    public void addValidator(IValidator<T> validator) {
        this.validators.add(validator);
    }

    @Override
    public ValidatorResult validate(T object) {
        for (IValidator<T> validator : this.validators) {
            ValidatorResult result = validator.validate(object);
            if (!result.isValid()) {
                return ValidatorResult.invalid(result.message());
            }
        }
        return ValidatorResult.valid();
    }
}
```

## Phase 3: Builder Pattern for Fluent API
- Goal: Provide easy-to-use fluent API for building validator chains.
- Pattern: Builder.
```java
public abstract class AbstractValidatorBuilder<T, B extends AbstractValidatorBuilder<T, B>> {
    protected IValidatorManager<T> manager = new ValidatorComposite<>();

    protected abstract B self();

    public B require() {
        manager.addValidator(new RequireValidator<>());
        return self();
    }

    public B custom(IValidator<T> validator) {
        manager.addValidator(validator);
        return self();
    }

    public B build() {
        return self();
    }

    public void validate(ValidatorContext<T> value) {
        manager.validate(value);
    }

    public ValidatorResult validate(T value) {
        return manager.validate(value);
    }
}

public class StringValidatorBuilder extends AbstractValidatorBuilder<String, StringValidatorBuilder> {
    private StringValidatorBuilder() {}

    @Override
    protected StringValidatorBuilder self() {
        return this;
    }

    public static StringValidatorBuilder builder() {
        return new StringValidatorBuilder();
    }

    public StringValidatorBuilder email() {
        manager.addValidator(new EmailValidator());
        return self();
    }

    public StringValidatorBuilder minLength(int minLength) {
        manager.addValidator(new MinLengthValidator(minLength));
        return self();
    }

    // Other methods...
}
```

## Phase 4: Validation Context & Execution
- Goal: Manage validation state and collect errors.
```java
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
```
        
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
  - Create custom validators by implementing IValidator.
  - Use builders to chain validations.
  - Build a console demo application.

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
### 2. Design Pattern Explanation (Important - Minimum 3 patterns):
   - Pattern Name.
   - Class diagram corresponding to each pattern (Extract from the general diagram).
   - Code snippets using the pattern (Copy code from Phases 1-4).
   - Meaning/Significance of using the pattern (e.g., Why use Strategy for Validator? -> To easily extend new rules without modifying old code).

