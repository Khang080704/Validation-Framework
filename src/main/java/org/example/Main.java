package org.example;


import org.example.configproviders.AnnotationConfigProvider;
import org.example.configproviders.CachedConfigProvider;
import org.example.configproviders.ConfigProvider;
import org.example.entities.Credential;
import org.example.validators.Validator;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ConfigProvider annotationConfigProvider = new AnnotationConfigProvider();
        ConfigProvider configProvider = new CachedConfigProvider(List.of(
                annotationConfigProvider
        ));
        Validator validator = new Validator(configProvider);

        Credential credential = new Credential();
        credential.setUsername("username");

        Map<String, List<String>> violations = validator.validate(credential);
        System.out.println(violations);
    }
}
