package org.example;


import org.example.annotations.Max;
import org.example.annotations.Min;
import org.example.annotations.Size;
import org.example.builder.StringValidatorBuilder;
import org.example.core.ValidatorContext;
import org.example.core.ValidatorResult;
import org.example.validators.Basic.Regex.RegexValidator;

import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    @Min(value = 4)
    private String name;

    @Size(min = 4, max = 10)
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        User user = new User("khang", 21);
        List<ValidatorResult> result = ValidatorContext.validateObject(user);
        if(result.isEmpty()) {
            System.out.println("user valid");
        }
        else {
            for (ValidatorResult validatorResult : result) {
                System.out.println(validatorResult.message());
            }
        }

    }
}
