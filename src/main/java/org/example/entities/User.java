package org.example.entities;

import org.example.constraints.annotation.*;

import java.util.List;

public class User {
    @IsValid
    private Credential credential;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    private String middleName;

    @Email(message = "Email must be a valid email address")
    private String email;

    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 100, message = "Age must be at most 100")
    private int age;

    @Size(min = 1, max = 3, message = "There must be between 1 and 3 phone numbers")
    @NotEmpty(message = "Phone numbers must not be empty")
    private List<String> phoneNumbers;

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
