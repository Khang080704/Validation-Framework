# Java Validation Framework

## Course Project: Design Patterns


# 📖 Introduction

Java Validation Framework is a lightweight, open-source library designed to assist developers in validating data within Java applications (Swing, JavaFX, Console, etc.).

This project is inspired by Hibernate Validator and jQuery Validation, with the core objective of flexibly applying Design Patterns to ensure usability, extensibility, and maintainability.

# 🚀 Key Features

- Annotation Support: Define data constraints directly on the Model using Annotations (e.g., @Required, @Email, @Min, @Max).

- Fluent API: Support validation configuration via method chaining.

- Composite Validation: Combine multiple validation rules for a single data field.

- Custom Rules: Easily create and integrate custom validators.

- Regex Support: Support complex pattern matching using Regular Expressions.

- UI Integration: Automatic error notification mechanism for the User Interface (Observer Pattern).

- Extensibility: Loosely coupled architecture, allowing easy addition of new rules without modifying core code.

# 🛠️ Technology & Design Patterns

The project is built using pure Java Core (no third-party framework dependencies) and implements at least 4 GoF Design Patterns:

- Strategy Pattern: Handles different validation algorithms (Email, Range, Null check, etc.).

- Composite Pattern: Groups and manages multiple validators on a single object.

- Observer Pattern: Listens for data changes and updates error notifications on the UI.

- Factory Method / Singleton: Manages the initialization and provision of Validator instances.

# 📦 Folder Structure

In accordance with submission regulations, the Repository is organized as follows:
```
Validation-Framework/
├── 1.Documents/         # Project Report, Class Diagrams, Slides
├── 2.Source code/       # Source Code (Java Project)
├── 3.Functions List/    # List of features and completion status
└── 4.Others/            # Demo Video, executable setup file (if any)
```




# ⚡ Quick Start & Usage

## 1. Installation

Clone this repository to your machine and import it into your IDE (Eclipse/IntelliJ/NetBeans) as a Java Project.

```bash
git clone https://github.com/Khang080704/Validation-Framework.git
```


## 2. Method 1: Using Annotations (Recommended)

Step 1: Add Annotations to your Java Model (POJO).
```java
public class User {
@Required(message = "Username cannot be empty")
@Length(min = 6, max = 20)
private String username;

    @Required
    @Email(message = "Invalid email format")
    private String email;

    @Range(min = 18, max = 100)
    private int age;
    
    // Getters and Setters...
}
```

Step 2: Perform Validation.
```
User user = new User();
user.setUsername("admin");
user.setAge(15); // Invalid

// Call Validation Context
List<ValidationResult> errors = ValidationContext.validate(user);

if (!errors.isEmpty()) {
    for (ValidationResult error : errors) {
        System.out.println(error.getMessage());
    }
} else {
    System.out.println("Data is valid!");
}
```



## 3. Method 2: Manual Code (Fluent/Manual)
```
// Validate a specific value
IValidator<String> emailValidator = ValidatorFactory.createEmailValidator();
ValidationResult result = emailValidator.validate("invalid-email");

if (!result.isValid()) {
    System.out.println("Error: " + result.getMessage());
}
```



## 4. Creating Custom Validators

You can define a new rule by implementing the IValidator interface:

```java
public class ZipCodeValidator implements IValidator<String> {
    @Override
    public ValidationResult validate(String value) {
        if (value != null && value.matches("\\d{5}")) {
            return new ValidationResult(true, "");
        }
        return new ValidationResult(false, "Zip code must contain 5 digits");
    }
}
```

# 📄 License

This project is created for educational purposes at HCMUS.