package org.example;

import org.example.common.ValidationViolation;
import org.example.constraintbuilder.ConstraintBuilder;
import org.example.constraints.definition.*;
import org.example.entities.Category;
import org.example.entities.Credential;
import org.example.entities.Product;
import org.example.entities.User;
import org.example.providers.AnnotationClassValidatorProvider;
import org.example.providers.ProgrammaticClassValidatorProvider;
import org.example.validators.Validator;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AnnotationClassValidatorProvider annotationClassValidatorProvider = new AnnotationClassValidatorProvider();
        ProgrammaticClassValidatorProvider programmaticClassValidatorProvider = new ProgrammaticClassValidatorProvider();
        Validator validator = new Validator(List.of(
                annotationClassValidatorProvider,
                programmaticClassValidatorProvider
        ));

        Credential credential = new Credential();
        credential.setUsername("username");
        credential.setPassword("");

        User user = new User();
        user.setCredential(credential);
        user.setAge(-1);
        user.setEmail("example");
        user.setLastName("   ");

        List<String> phoneNumbers = List.of("1234567890", "1234567890", "1234567890", "1234567890", "1234567890", "1234567890");
        user.setPhoneNumbers(phoneNumbers);
        user.setActive(false);

        List<ValidationViolation> violations = validator.validate(user);
//        List<ValidationViolation> violations = validator.validateProperty(user, "credential");
//        List<ValidationViolation> violations = validator.validateValue(Credential.class, "   ", "username");
        for (ValidationViolation violation : violations) {
            System.out.println("Field: " + violation.getPath());
            for (String message : violation.getMessages()) {
                System.out.println(" - " + message);
            }
        }

        /*-------------------------------------------------------------------------*/
//        ConstraintBuilder categoryConstraintBuilder = new ConstraintBuilder(programmaticClassValidatorProvider);
//        categoryConstraintBuilder
//                .on(Category.class)
//                .constraints(
//                        "name",
//                        new NotNullDefinition().message("Category name must not be null"),
//                        new NotBlankDefinition().message("Category name must not be blank")
//                )
//                .constraints(
//                        "id",
//                        new MinDefinition().value(1).message("Category ID must be at least 1")
//                )
//                .build();
//
//        ConstraintBuilder productConstraintBuilder = new ConstraintBuilder(programmaticClassValidatorProvider);
//        productConstraintBuilder
//                .on(Product.class)
//                .constraints(
//                        "name",
//                        new NotNullDefinition().message("Product name must not be null"),
//                        new NotBlankDefinition().message("Product name must not be blank")
//                )
//                .constraints(
//                        "quantity",
//                        new MinDefinition().value(0).message("Quantity must be at least 0"),
//                        new MaxDefinition().value(1000).message("Quantity must be at most 1000"),
//                        new NotNullDefinition().message("Quantity must not be null")
//                )
//                .constraints(
//                        "category",
//                        new IsValidDefinition()
//                )
//                .build();
//
//        Product product = new Product();
//        product.setName("   ");
//        product.setQuantity(1500);
//
//        Category category = new Category();
//        category.setName(null);
//        category.setId(-1);
//
//        product.setCategory(category);
//
//        List<ValidationViolation> productViolations = validator.validate(product);
//        for (ValidationViolation violation : productViolations) {
//            System.out.println("Field: " + violation.getPath());
//            for (String message : violation.getMessages()) {
//                System.out.println(" - " + message);
//            }
//        }
    }
}
