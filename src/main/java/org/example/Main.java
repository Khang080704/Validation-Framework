package org.example;

import org.example.concrete.NumberValidator.MinValidator;
import org.example.concrete.NumberValidator.RangeValidator;
import org.example.concrete.StringValidator.LengthValidator;
import org.example.concrete.regex.RegexValidator;
import org.example.core.IValidator;
import org.example.core.ValidatorResult;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter your name:");
        String name = sc.nextLine();

        IValidator<String> stringValidator = new LengthValidator(5, 100);
        ValidatorResult result = stringValidator.validate(name);

        if(result.isValid()) {
            System.out.println("your name is " + name);
        }
        else {
            System.out.println(result.message());
        }

        System.out.println("please enter your age:");
        int age = sc.nextInt();
        IValidator<Integer> numberValidator = new MinValidator<>(5);
        result = numberValidator.validate(age);
        if(result.isValid()) {
            System.out.println("your age is " + age);
        }
        else {
            System.out.println(result.message());
        }

        System.out.println("please enter your email");
        String email = sc.next();
        try {
            IValidator<String> emailValidator = new RegexValidator("^[^@]+@[^@]+\\.[^@]+$");
            result = emailValidator.validate(email);
            if(result.isValid()) {
                System.out.println("your email is " + email);
            }
            else {
                System.out.println(result.message());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
