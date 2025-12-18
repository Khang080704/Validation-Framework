package org.example;


import org.example.annotations.*;
import org.example.builder.NumberValidatorBuilder;
import org.example.builder.StringValidatorBuilder;
import org.example.core.IValidator;
import org.example.core.IValidatorManager;
import org.example.core.ValidatorContext;
import org.example.core.ValidatorResult;
import org.example.validators.Basic.NumberValidator.MaxValidator;
import org.example.validators.Basic.NumberValidator.MinValidator;
import org.example.validators.Basic.NumberValidator.RangeValidator;
import org.example.validators.Basic.Regex.RegexValidator;
import org.example.validators.Basic.StringValidator.*;
import org.example.validators.ComplexValidor.EmailValidator;
import org.example.validators.Composite.ValidatorComposite;

import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("VALIDATION FRAMEWORK - DEMO EXAMPLES");
        System.out.println("=".repeat(80));

        // 1. Simple String Validators
        demoMaxLengthValidator();
        demoMinLengthValidator();
        demoLengthValidator();
        demoUpperCaseValidator();
        demoRequireValidator();

        // 2. Number Validators
        demoMaxValidator();
        demoMinValidator();
        demoRangeValidator();

        // 3. Complex Validators
        demoEmailValidator();
        demoRegexValidator();

        // 4. Composite Validators (Multiple validations)
        demoCompositeValidator();

        // 5. Builder Pattern
        demoStringValidatorBuilder();
        demoNumberValidatorBuilder();

        // 6. Validator Context (Collecting multiple errors)
        demoValidatorContext();

        // 7. Annotation-based Validation
        demoAnnotationValidation();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("DEMO COMPLETED");
        System.out.println("=".repeat(80));
    }

    // =====================================================================
    // 1. STRING VALIDATORS
    // =====================================================================

    private static void demoMaxLengthValidator() {
        System.out.println("\n[1] Demo: MaxLengthValidator");
        System.out.println("-".repeat(50));

        IValidator<String> validator = new MaxLengthValidator(10);

        String validInput = "Hello";
        String invalidInput = "This is a very long text";

        ValidatorResult result1 = validator.validate(validInput);
        System.out.println("Input: \"" + validInput + "\" -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = validator.validate(invalidInput);
        System.out.println("Input: \"" + invalidInput + "\" -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));
    }

    private static void demoMinLengthValidator() {
        System.out.println("\n[2] Demo: MinLengthValidator");
        System.out.println("-".repeat(50));

        IValidator<String> validator = new MinLengthValidator(5);

        String validInput = "Hello World";
        String invalidInput = "Hi";

        ValidatorResult result1 = validator.validate(validInput);
        System.out.println("Input: \"" + validInput + "\" -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = validator.validate(invalidInput);
        System.out.println("Input: \"" + invalidInput + "\" -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));
    }

    private static void demoLengthValidator() {
        System.out.println("\n[3] Demo: LengthValidator (Range)");
        System.out.println("-".repeat(50));

        IValidator<String> validator = new LengthValidator(5, 15);

        String validInput = "Hello World";
        String tooShort = "Hi";
        String tooLong = "This is a very long text that exceeds limit";

        System.out.println("Input: \"" + validInput + "\" -> " + 
                          (validator.validate(validInput).isValid() ? "VALID ✓" : "INVALID ✗"));
        
        ValidatorResult result2 = validator.validate(tooShort);
        System.out.println("Input: \"" + tooShort + "\" -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));

        ValidatorResult result3 = validator.validate(tooLong);
        System.out.println("Input: \"" + tooLong + "\" -> " + 
                          (result3.isValid() ? "VALID ✓" : "INVALID ✗ - " + result3.message()));
    }

    private static void demoUpperCaseValidator() {
        System.out.println("\n[4] Demo: UpperCaseValidator");
        System.out.println("-".repeat(50));

        IValidator<String> validator = new UpperCaseValidator();

        String validInput = "Hello World";
        String invalidInput = "hello world";

        ValidatorResult result1 = validator.validate(validInput);
        System.out.println("Input: \"" + validInput + "\" -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = validator.validate(invalidInput);
        System.out.println("Input: \"" + invalidInput + "\" -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));
    }

    private static void demoRequireValidator() {
        System.out.println("\n[5] Demo: RequireValidator (Not Null)");
        System.out.println("-".repeat(50));

        IValidator<String> validator = new RequireValidator<>();

        String validInput = "Some value";
        String nullInput = null;

        ValidatorResult result1 = validator.validate(validInput);
        System.out.println("Input: \"" + validInput + "\" -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = validator.validate(nullInput);
        System.out.println("Input: null -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));
    }

    // =====================================================================
    // 2. NUMBER VALIDATORS
    // =====================================================================

    private static void demoMaxValidator() {
        System.out.println("\n[6] Demo: MaxValidator (Numbers)");
        System.out.println("-".repeat(50));

        IValidator<Integer> validator = new MaxValidator<>(100);

        Integer validInput = 50;
        Integer invalidInput = 150;

        ValidatorResult result1 = validator.validate(validInput);
        System.out.println("Input: " + validInput + " -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = validator.validate(invalidInput);
        System.out.println("Input: " + invalidInput + " -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));
    }

    private static void demoMinValidator() {
        System.out.println("\n[7] Demo: MinValidator (Numbers)");
        System.out.println("-".repeat(50));

        IValidator<Integer> validator = new MinValidator<>(10);

        Integer validInput = 50;
        Integer invalidInput = 5;

        ValidatorResult result1 = validator.validate(validInput);
        System.out.println("Input: " + validInput + " -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = validator.validate(invalidInput);
        System.out.println("Input: " + invalidInput + " -> " + 
                          (result2.isValid() ? "VALID ✗ - " : "VALID ✓") + result2.message());
    }

    private static void demoRangeValidator() {
        System.out.println("\n[8] Demo: RangeValidator (Numbers)");
        System.out.println("-".repeat(50));

        IValidator<Integer> validator = new RangeValidator<>(10, 100);

        Integer validInput = 50;
        Integer tooSmall = 5;
        Integer tooLarge = 150;

        System.out.println("Input: " + validInput + " -> " + 
                          (validator.validate(validInput).isValid() ? "VALID ✓" : "INVALID ✗"));

        ValidatorResult result2 = validator.validate(tooSmall);
        System.out.println("Input: " + tooSmall + " -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));

        ValidatorResult result3 = validator.validate(tooLarge);
        System.out.println("Input: " + tooLarge + " -> " + 
                          (result3.isValid() ? "VALID ✓" : "INVALID ✗ - " + result3.message()));
    }

    // =====================================================================
    // 3. COMPLEX VALIDATORS
    // =====================================================================

    private static void demoEmailValidator() {
        System.out.println("\n[9] Demo: EmailValidator");
        System.out.println("-".repeat(50));

        IValidator<String> validator = new EmailValidator();

        String validEmail = "user@example.com";
        String noAt = "userexample.com";
        String noDot = "user@example";
        String withSpace = "user @example.com";

        ValidatorResult result1 = validator.validate(validEmail);
        System.out.println("Input: \"" + validEmail + "\" -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = validator.validate(noAt);
        System.out.println("Input: \"" + noAt + "\" -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));

        ValidatorResult result3 = validator.validate(noDot);
        System.out.println("Input: \"" + noDot + "\" -> " + 
                          (result3.isValid() ? "VALID ✓" : "INVALID ✗ - " + result3.message()));

        ValidatorResult result4 = validator.validate(withSpace);
        System.out.println("Input: \"" + withSpace + "\" -> " + 
                          (result4.isValid() ? "VALID ✓" : "INVALID ✗ - " + result4.message()));
    }

    private static void demoRegexValidator() {
        System.out.println("\n[10] Demo: RegexValidator (Phone Pattern)");
        System.out.println("-".repeat(50));

        // Pattern for Vietnamese phone numbers: 10 digits starting with 0
        IValidator<String> validator = new RegexValidator("^0\\d{9}$");

        String validPhone = "0912345678";
        String invalidPhone1 = "123456789";  // doesn't start with 0
        String invalidPhone2 = "09123";      // too short

        ValidatorResult result1 = validator.validate(validPhone);
        System.out.println("Input: \"" + validPhone + "\" -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = validator.validate(invalidPhone1);
        System.out.println("Input: \"" + invalidPhone1 + "\" -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));

        ValidatorResult result3 = validator.validate(invalidPhone2);
        System.out.println("Input: \"" + invalidPhone2 + "\" -> " + 
                          (result3.isValid() ? "VALID ✓" : "INVALID ✗ - " + result3.message()));
    }

    // =====================================================================
    // 4. COMPOSITE VALIDATOR (Multiple validations)
    // =====================================================================

    private static void demoCompositeValidator() {
        System.out.println("\n[11] Demo: CompositeValidator (Multiple Rules)");
        System.out.println("-".repeat(50));

        // Create a composite validator for password: 
        // - Required (not null)
        // - Min length 8
        // - Max length 20
        // - Must contain uppercase
        ValidatorComposite<String> passwordValidator = new ValidatorComposite<>();
        passwordValidator.addValidator(new RequireValidator<>());
        passwordValidator.addValidator(new MinLengthValidator(8));
        passwordValidator.addValidator(new MaxLengthValidator(20));
        passwordValidator.addValidator(new UpperCaseValidator());

        String validPassword = "MyPassword123";
        String tooShort = "Pass1";
        String noUppercase = "password123";
        String nullPassword = null;

        ValidatorResult result1 = passwordValidator.validate(validPassword);
        System.out.println("Password: \"" + validPassword + "\" -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = passwordValidator.validate(tooShort);
        System.out.println("Password: \"" + tooShort + "\" -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));

        ValidatorResult result3 = passwordValidator.validate(noUppercase);
        System.out.println("Password: \"" + noUppercase + "\" -> " + 
                          (result3.isValid() ? "VALID ✓" : "INVALID ✗ - " + result3.message()));

        ValidatorResult result4 = passwordValidator.validate(nullPassword);
        System.out.println("Password: null -> " + 
                          (result4.isValid() ? "VALID ✓" : "INVALID ✗ - " + result4.message()));
    }

    // =====================================================================
    // 5. BUILDER PATTERN (Fluent API)
    // =====================================================================

    private static void demoStringValidatorBuilder() {
        System.out.println("\n[12] Demo: StringValidatorBuilder (Fluent API)");
        System.out.println("-".repeat(50));

        // Build a username validator: required, 5-20 chars, must have uppercase
        StringValidatorBuilder usernameValidator = StringValidatorBuilder.builder()
                .require()
                .minLength(5)
                .maxLength(20)
                .upperCase()
                .build();

        String validUsername = "UserName123";
        String tooShort = "usr";
        String noUppercase = "username123";

        ValidatorResult result1 = usernameValidator.validate(validUsername);
        System.out.println("Username: \"" + validUsername + "\" -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = usernameValidator.validate(tooShort);
        System.out.println("Username: \"" + tooShort + "\" -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));

        ValidatorResult result3 = usernameValidator.validate(noUppercase);
        System.out.println("Username: \"" + noUppercase + "\" -> " + 
                          (result3.isValid() ? "VALID ✓" : "INVALID ✗ - " + result3.message()));

        // Build an email validator using builder
        System.out.println("\nEmail validation with builder:");
        StringValidatorBuilder emailValidator = StringValidatorBuilder.builder()
                .require()
                .email()
                .build();

        String validEmail = "test@example.com";
        String invalidEmail = "invalid-email";

        ValidatorResult result4 = emailValidator.validate(validEmail);
        System.out.println("Email: \"" + validEmail + "\" -> " + 
                          (result4.isValid() ? "VALID ✓" : "INVALID ✗"));

        ValidatorResult result5 = emailValidator.validate(invalidEmail);
        System.out.println("Email: \"" + invalidEmail + "\" -> " + 
                          (result5.isValid() ? "VALID ✓" : "INVALID ✗ - " + result5.message()));
    }

    private static void demoNumberValidatorBuilder() {
        System.out.println("\n[13] Demo: NumberValidatorBuilder (Fluent API)");
        System.out.println("-".repeat(50));

        // Build an age validator: required, 18-100
        NumberValidatorBuilder<Integer> ageValidator = NumberValidatorBuilder.<Integer>builder()
                .require()
                .range(18, 100)
                .build();

        Integer validAge = 25;
        Integer tooYoung = 15;
        Integer tooOld = 150;
        Integer nullAge = null;

        ValidatorResult result1 = ageValidator.validate(validAge);
        System.out.println("Age: " + validAge + " -> " + 
                          (result1.isValid() ? "VALID ✓" : "INVALID ✗ - " + result1.message()));

        ValidatorResult result2 = ageValidator.validate(tooYoung);
        System.out.println("Age: " + tooYoung + " -> " + 
                          (result2.isValid() ? "VALID ✓" : "INVALID ✗ - " + result2.message()));

        ValidatorResult result3 = ageValidator.validate(tooOld);
        System.out.println("Age: " + tooOld + " -> " + 
                          (result3.isValid() ? "VALID ✓" : "INVALID ✗ - " + result3.message()));

        ValidatorResult result4 = ageValidator.validate(nullAge);
        System.out.println("Age: null -> " + 
                          (result4.isValid() ? "VALID ✓" : "INVALID ✗ - " + result4.message()));

        // Build a price validator: min 0, max 1000000
        System.out.println("\nPrice validation with builder:");
        NumberValidatorBuilder<Double> priceValidator = NumberValidatorBuilder.<Double>builder()
                .require()
                .min(0.0)
                .max(1000000.0)
                .build();

        Double validPrice = 500.0;
        Double negativePrice = -10.0;

        ValidatorResult result5 = priceValidator.validate(validPrice);
        System.out.println("Price: " + validPrice + " -> " + 
                          (result5.isValid() ? "VALID ✓" : "INVALID ✗"));

        ValidatorResult result6 = priceValidator.validate(negativePrice);
        System.out.println("Price: " + negativePrice + " -> " + 
                          (result6.isValid() ? "VALID ✓" : "INVALID ✗ - " + result6.message()));
    }

    // =====================================================================
    // 6. VALIDATOR CONTEXT (Collecting multiple errors)
    // =====================================================================

    private static void demoValidatorContext() {
        System.out.println("\n[14] Demo: ValidatorContext (Collect Multiple Errors)");
        System.out.println("-".repeat(50));

        // Create a form validator that collects all errors
        String formData = "ab";  // Invalid: too short, no uppercase

        ValidatorContext<String> context = new ValidatorContext<>(formData);

        // Add multiple validators
        IValidatorManager<String> composite = new ValidatorComposite<>();
        composite.addValidator(new MinLengthValidator(5));
        composite.addValidator(new UpperCaseValidator());
        composite.addValidator(new MaxLengthValidator(20));
        composite.validate(context);

        System.out.println("Input: \"" + formData + "\"");
        if (context.hasErrors()) {
            System.out.println("INVALID ✗ - Found " + context.getErrors().size() + " error(s):");
            for (int i = 0; i < context.getErrors().size(); i++) {
                System.out.println("  " + (i + 1) + ". " + context.getErrors().get(i));
            }
        } else {
            System.out.println("VALID ✓");
        }

        // Try with valid data
        System.out.println("\nTrying with valid data:");
        String validData = "ValidData123";
        ValidatorContext<String> context2 = new ValidatorContext<>(validData);
        composite.validate(context2);

        System.out.println("Input: \"" + validData + "\"");
        if (context2.hasErrors()) {
            System.out.println("INVALID ✗ - Errors:");
            for (String error : context2.getErrors()) {
                System.out.println("  - " + error);
            }
        } else {
            System.out.println("VALID ✓ - All validations passed!");
        }
    }

    // =====================================================================
    // 7. ANNOTATION-BASED VALIDATION
    // =====================================================================

    private static void demoAnnotationValidation() {
        System.out.println("\n[15] Demo: Annotation-Based Validation");
        System.out.println("-".repeat(50));

        // Create test objects with annotations
        User validUser = new User(
            "john.doe@example.com",
            "John Doe",
            25,
            "HelloWorld123"
        );

        User invalidUser = new User(
            null,              // invalid: null email
            "Jo",              // invalid: too short
            15,                // invalid: less than min age
            "password"         // invalid: exceeds max length? (depends on @Max value)
        );

        System.out.println("Validating VALID user:");
        List<ValidatorResult> errors1 = ValidatorContext.validateObject(validUser);
        if (errors1.isEmpty()) {
            System.out.println("✓ User is VALID - All validations passed!");
        } else {
            System.out.println("✗ User is INVALID - Errors found:");
            for (ValidatorResult error : errors1) {
                System.out.println("  - " + error.message());
            }
        }

        System.out.println("\nValidating INVALID user:");
        List<ValidatorResult> errors2 = ValidatorContext.validateObject(invalidUser);
        if (errors2.isEmpty()) {
            System.out.println("✓ User is VALID");
        } else {
            System.out.println("✗ User is INVALID - Found " + errors2.size() + " error(s):");
            for (int i = 0; i < errors2.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + errors2.get(i).message());
            }
        }

        // Demo Product validation
        System.out.println("\n" + "-".repeat(50));
        System.out.println("Validating Product with annotations:");

        Product validProduct = new Product("Laptop", 1500.0, "PROD-001");
        Product invalidProduct = new Product(null, -100.0, "");

        System.out.println("\nValid product:");
        List<ValidatorResult> productErrors1 = ValidatorContext.validateObject(validProduct);
        if (productErrors1.isEmpty()) {
            System.out.println("✓ Product is VALID");
        } else {
            System.out.println("✗ Product has errors:");
            productErrors1.forEach(e -> System.out.println("  - " + e.message()));
        }

        System.out.println("\nInvalid product:");
        List<ValidatorResult> productErrors2 = ValidatorContext.validateObject(invalidProduct);
        if (productErrors2.isEmpty()) {
            System.out.println("✓ Product is VALID");
        } else {
            System.out.println("✗ Product has " + productErrors2.size() + " error(s):");
            productErrors2.forEach(e -> System.out.println("  - " + e.message()));
        }
    }

    // =====================================================================
    // SAMPLE CLASSES WITH ANNOTATIONS
    // =====================================================================

    static class User {
        @NotNull
        @Email
        private String email;

        @NotEmpty
        @Size(min = 3, max = 50)
        private String name;

        @NotNull
        @Min(value = 18, message = "User must be at least 18 years old")
        private Integer age;

        @NotEmpty
        @Size(min = 8, max = 20)
        private String password;

        public User(String email, String name, Integer age, String password) {
            this.email = email;
            this.name = name;
            this.age = age;
            this.password = password;
        }

        // Getters
        public String getEmail() { return email; }
        public String getName() { return name; }
        public Integer getAge() { return age; }
        public String getPassword() { return password; }
    }

    static class Product {
        @NotNull(message = "Product name is required")
        @Size(min = 1, max = 100)
        private String name;

        @NotNull
        @Min(value = 0, message = "Price must be positive")
        private Double price;

        @NotEmpty
        @Pattern(regex = "^[A-Z]{4}-\\d{3}$")  // Format: PROD-001
        private String sku;

        public Product(String name, Double price, String sku) {
            this.name = name;
            this.price = price;
            this.sku = sku;
        }

        // Getters
        public String getName() { return name; }
        public Double getPrice() { return price; }
        public String getSku() { return sku; }
    }
}
