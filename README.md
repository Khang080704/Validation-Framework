# Java Validation Framework

## Course Project: Design Patterns

# 📖 Introduction

Java Validation Framework is a lightweight, open-source library designed to assist developers in validating data within Java applications (Swing, JavaFX, Console, etc.).

This project is inspired by Hibernate Validator and jQuery Validation, with the core objective of flexibly applying Design Patterns to ensure usability, extensibility, and maintainability.

# 🚀 Key Features

- Fluent API: Support validation configuration via method chaining using Builder Pattern.

- Composite Validation: Combine multiple validation rules for a single data field.

- Custom Rules: Easily create and integrate custom validators.

- Regex Support: Support complex pattern matching using Regular Expressions.

- Extensibility: Loosely coupled architecture, allowing easy addition of new rules without modifying core code.

# 🛠️ Technology & Design Patterns

The project is built using pure Java Core (no third-party framework dependencies) and implements at least 3 GoF Design Patterns:

- Strategy Pattern: Defines the common IValidator interface. Concrete classes contain specific validation logic.

- Composite Pattern: Groups and manages multiple validators on a single object.

- Builder Pattern: Facilitates the construction of complex validator chains through fluent API.

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

## 2. Method 1: Using Fluent API (Builder Pattern)

### String Validation
```java
StringValidatorBuilder builder = StringValidatorBuilder.builder()
    .require()
    .minLength(8)
    .maxLength(20)
    .upperCase()
    .email()
    .custom(new RegexValidator("custom_pattern"))
    .build();

ValidatorContext<String> context = new ValidatorContext<>("input_value");
builder.validate(context);

if (context.hasErrors()) {
    List<String> errors = context.getErrors();
    for (String error : errors) {
        System.out.println(error);
    }
} else {
    System.out.println("Data is valid!");
}
```

### Number Validation
```java
NumberValidatorBuilder<Integer> builder = NumberValidatorBuilder.builder()
    .min(0)
    .max(100)
    .range(10, 90)
    .build();

ValidatorContext<Integer> context = new ValidatorContext<>(50);
builder.validate(context);

if (context.hasErrors()) {
    // Handle errors
}
```

## 3. Creating Custom Validators

You can define a new rule by implementing the IValidator interface:

```java
public class ZipCodeValidator implements IValidator<String> {
    @Override
    public ValidatorResult validate(String value) {
        if (value != null && value.matches("\\d{5}")) {
            return ValidatorResult.valid();
        }
        return ValidatorResult.invalid("Zip code must contain 5 digits");
    }
}
```

Then use it in the builder:
```java
StringValidatorBuilder.builder()
    .custom(new ZipCodeValidator())
    .build();
```

# 📄 License

This project is created for educational purposes at HCMUS.