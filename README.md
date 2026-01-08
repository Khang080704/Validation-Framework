# Java Validation Framework

## Course Project: Design Patterns



# Quick Start & Usage

## 1. Installation

Clone this repository to your machine and import it into your IDE (Eclipse/IntelliJ/NetBeans) as a Java Project.

```bash
git clone https://github.com/Khang080704/Validation-Framework.git
```

## 2. Setup Validator

First, create a `Validator` instance with both annotation and programmatic providers:

```java
import org.example.providers.AnnotationClassValidatorProvider;
import org.example.providers.ProgrammaticClassValidatorProvider;
import org.example.validators.Validator;

// Create providers
AnnotationClassValidatorProvider annotationProvider = new AnnotationClassValidatorProvider();
ProgrammaticClassValidatorProvider programmaticProvider = new ProgrammaticClassValidatorProvider();

// Create validator with both providers
Validator validator = new Validator(List.of(
    annotationProvider,
    programmaticProvider
));
```

---

# Annotation-Based Validation

## How to Use Annotation Validation

### Step 1: Add Annotations to Your Model

```java
import org.example.constraints.annotation.*;

public class User {
    @NotBlank(message = "First name must not be blank")
    private String firstName;
    
    @NotBlank(message = "Last name must not be blank")
    private String lastName;
    
    @Email(message = "Email must be a valid email address")
    private String email;
    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private int age;
    
    @Size(min = 1, max = 3, message = "Must have 1-3 phone numbers")
    @NotEmpty(message = "Phone numbers cannot be empty")
    private List<String> phoneNumbers;
    
    @AssertTrue(message = "User must be active")
    private boolean active;
    
    @IsValid  // Nested validation
    private Address address;
    
    // Getters and Setters...
}
```

### Step 2: Perform Validation

**Option 1.** Validate object

```java
import org.example.common.ValidationViolation;

User user = new User();
user.setFirstName("");
user.setEmail("invalid-email");
user.setAge(15);
user.setActive(false);

List<ValidationViolation> violations = validator.validate(user);
```

**Option 2.** Validate Specific Property

```java
List<ValidationViolation> violations = validator.validateProperty(user, "email");
```

**Option 3.** Validate Value Without Object

```java
List<ValidationViolation> violations = validator.validateValue(User.class, "invalid", "email");
```

## Available Built-in Annotations

| Annotation | Supported Types | Description | Example |
|------------|----------------|-------------|---------|
| `@NotNull` | Any | Field must not be null | `@NotNull` |
| `@NotEmpty` | String, Collection, Map, Arrays | Must not be null or empty | `@NotEmpty` |
| `@NotBlank` | String | Must not be null, empty, or whitespace only | `@NotBlank` |
| `@Email` | String | Must be a valid email format | `@Email` |
| `@Pattern` | String | Must match regex pattern | `@Pattern(regex = "\\d{3}-\\d{4}")` |
| `@Size` | String, Collection, Map, Arrays | Size/length must be within range | `@Size(min = 2, max = 10)` |
| `@Min` | Number (int, long, double, etc.) | Value must be >= min | `@Min(value = 18)` |
| `@Max` | Number | Value must be <= max | `@Max(value = 100)` |
| `@AssertTrue` | Boolean | Must be true | `@AssertTrue` |
| `@AssertFalse` | Boolean | Must be false | `@AssertFalse` |
| `@IsValid` | Any object | Validates nested objects | `@IsValid` |

## How to Create Custom Annotation Validators

### Step 1: Create Custom Annotation

```java
package com.example.constraints.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PhoneNumber {
    String message() default "Invalid phone number format";
}
```

### Step 2: Create Custom Constraint Validator

```java
package com.example.constraints.validators;

import org.example.constraints.validators.ConstraintValidator;

public class PhoneNumberValidator extends ConstraintValidator<String> {
    
    @Override
    public String validate(String value) {
        if (value == null) {
            return null; 
        }
        
        if (value.matches("\\d{10}")) {
            return null; 
        }
        
        return message; 
    }
}
```

### Step 3: Register Custom Validator

```java
import org.example.constraints.validators.ConstraintValidatorRegistry;

ConstraintValidatorRegistry.register(
    PhoneNumber.class,      // Annotation class
    String.class,           // Field type
    PhoneNumberValidator.class  // Validator class
);
```

### Step 4: Use Your Custom Annotation

```java
public class Contact {
    @PhoneNumber(message = "Please enter a valid 10-digit phone number")
    private String phoneNumber;
}
```

---

# Programmatic Validation

## How to Use Programmatic Validation

Programmatic validation is useful when you can't modify existing classes or need dynamic validation rules.

### Step 1: Create Constraint Definitions

```java
import org.example.constraintbuilder.ConstraintBuilder;
import org.example.constraints.definition.*;

ConstraintBuilder builder = new ConstraintBuilder(programmaticProvider);

builder.on(Category.class)
    .constraints(
        "name",
        new NotNullDefinition().message("Category name must not be null"),
        new NotBlankDefinition().message("Category name must not be blank"),
        new SizeDefinition().min(3).max(50).message("Name must be 3-50 characters")
    )
    .constraints(
        "id",
        new MinDefinition().value(1).message("Category ID must be at least 1")
    )
    .build();
```

### Step 2: Validate Objects

```java
Category category = new Category();
category.setName("");
category.setId(-1);

List<ValidationViolation> violations = validator.validate(category);
```

## Available Built-in Definitions

| Definition Class | Parameters | Description |
|-----------------|------------|-------------|
| `NotNullDefinition` | `message()` | Field must not be null |
| `NotEmptyDefinition` | `message()` | Must not be null or empty |
| `NotBlankDefinition` | `message()` | Must not be null, empty, or whitespace |
| `EmailDefinition` | `message()` | Must be valid email format |
| `PatternDefinition` | `regex()`, `message()` | Must match regex pattern |
| `SizeDefinition` | `min()`, `max()`, `message()` | Size/length within range |
| `MinDefinition` | `value()`, `message()` | Numeric minimum value |
| `MaxDefinition` | `value()`, `message()` | Numeric maximum value |
| `AssertTrueDefinition` | `message()` | Must be true |
| `AssertFalseDefinition` | `message()` | Must be false |
| `IsValidDefinition` | - | Validates nested objects |

## How to Create Custom Programmatic Validators

### Step 1: Create Custom Definition Class

```java
package com.example.constraints.definition;

import org.example.constraints.definition.ConstraintDefinition;
import com.example.constraints.annotation.PhoneNumber;
import java.util.Map;

public class PhoneNumberDefinition extends ConstraintDefinition {
    
    public PhoneNumberDefinition() {
        this.annotationType = PhoneNumber.class;
        this.message = "Invalid phone number format";
    }
    
    public PhoneNumberDefinition message(String message) {
        this.message = message;
        return this;
    }
    
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("message", message);
    }
}
```

### Step 2: Register Your Custom Validator (if not already done)

```java
ConstraintValidatorRegistry.register(
    PhoneNumber.class,
    String.class,
    PhoneNumberValidator.class
);
```

### Step 3: Use Your Custom Definition

```java
ConstraintBuilder builder = new ConstraintBuilder(programmaticProvider);

builder.on(Contact.class)
    .constraints(
        "phoneNumber",
        new PhoneNumberDefinition().message("Invalid phone format"),
        new NotBlankDefinition().message("Phone number is required")
    )
    .build();

Contact contact = new Contact();
contact.setPhoneNumber("invalid");
List<ValidationViolation> violations = validator.validate(contact);
```

---

# 📄 License

This project is created for educational purposes at HCMUS.