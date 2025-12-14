package org.example;


import org.example.builder.NumberValidatorBuilder;
import org.example.builder.StringValidatorBuilder;
import org.example.core.ValidatorContext;
import org.example.core.ValidatorResult;
import org.example.validators.Basic.NumberValidator.RangeValidator;
import org.example.validators.Basic.Regex.RegexValidator;
import org.example.validators.Composite.ValidatorComposite;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        Scanner sc = new Scanner(System.in);

        System.out.println("please enter your password:");
        String password = sc.next();

        StringValidatorBuilder manager =
                StringValidatorBuilder.builder()
                                .require()
                                .minLength(8)
                                .upperCase()
                                .email()
                                .custom(new RegexValidator("asd"))
                                .build();
        ValidatorContext<String> context = new ValidatorContext<>(password);
        manager.validate(context);
        if(context.hasErrors()) {
            List<String> errors = context.getErrors();
            for (String error : errors) {
                System.out.println(error);
            }
        }
        else {
            System.out.println(password);
        }



        sc.close();
    }
}
