package org.example;


import org.example.builder.StringValidatorBuilder;
import org.example.core.IValidatorManager;
import org.example.core.ValidatorResult;

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

        IValidatorManager<String> manager =
                StringValidatorBuilder.builder()
                                .minLength(8)
                                .upperCase()
                                .build();

        ValidatorResult passwordResult = manager.validate(password);
        sc.close();
    }
}
