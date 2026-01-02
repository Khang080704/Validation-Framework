package org.example;

import org.example.configproviders.AnnotationConfigProvider;
import org.example.configproviders.CachedConfigProvider;
import org.example.configproviders.ConfigProvider;
import org.example.configproviders.ProgrammaticConfigProvider;
import org.example.constraintbuilder.ConstraintBuilder;
import org.example.constraints.definition.NotNullDefinition;
import org.example.entities.Credential;
import org.example.entities.User;
import org.example.validators.Validator;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigProvider annotationConfigProvider = new AnnotationConfigProvider();
        ProgrammaticConfigProvider programmaticConfigProvider = new ProgrammaticConfigProvider();
        ConfigProvider configProvider = new CachedConfigProvider(List.of(
                annotationConfigProvider,
                programmaticConfigProvider
        ));
        Validator validator = new Validator(configProvider);

        Credential credential = new Credential();
        credential.setUsername("username");

        Map<String, List<String>> violations = validator.validate(credential);
        System.out.println(violations);

        /*-------------------------------------------------------------------------*/

        ConstraintBuilder builder = new ConstraintBuilder(programmaticConfigProvider);
        builder
            .on(User.class)
            .constraints("firstName", new NotNullDefinition().message("First name is required"))
            .constraints("lastName", new NotNullDefinition().message("Last name is required"))
            .build();

        User user = new User();
        user.setFirstName("firstName");
        user.setMiddleName("middleName");
        Map<String, List<String>> userViolations = validator.validate(user);
        System.out.println(userViolations);
    }
}
