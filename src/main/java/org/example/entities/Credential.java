package org.example.entities;

import org.example.constraints.annotation.*;

public class Credential {
    @NotNull(message = "Username must not be null")
    @NotBlank(message = "Username must not be blank")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String username;

    @NotNull(message = "Password must not be null")
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
