package org.example.entities;

import org.example.constraints.annotation.Max;
import org.example.constraints.annotation.Min;
import org.example.constraints.annotation.NotNull;
import org.example.constraints.annotation.Pattern;

public class Credential {
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
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
