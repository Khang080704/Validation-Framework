package org.example;

import org.example.common.ValidationViolation;
import org.example.constraintbuilder.ConstraintBuilder;
import org.example.constraints.definition.NotNullDefinition;
import org.example.entities.Credential;
import org.example.entities.User;
import org.example.providers.AnnotationClassValidatorProvider;
import org.example.providers.ProgrammaticClassValidatorProvider;

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

        ValidationViolation violations = validator.validate(credential);
        System.out.println(violations.getViolations());

        /*-------------------------------------------------------------------------*/
//        ConstraintBuilder builder = new ConstraintBuilder(programmaticClassValidatorProvider);
//        builder
//            .on(User.class)
//            .constraints("firstName", new NotNullDefinition().message("First name is required"))
//            .constraints("lastName", new NotNullDefinition().message("Last name is required"))
//            .build();
//
//        User user = new User();
//        user.setFirstName("firstName");
//        user.setMiddleName("middleName");
//        user.setEmail("email");
//
//        ValidationViolation userViolations = validator.validate(user);
//        System.out.println(userViolations.getViolations());

    }
}
