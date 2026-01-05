package org.example.entities;

import org.example.constraints.annotation.Email;
import org.example.constraints.annotation.Max;
import org.example.constraints.annotation.NotNull;

public class User {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;


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

}
