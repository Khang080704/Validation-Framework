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

    @NotNull(message = "phone must be not null")
    @Pattern(regex = "", message = "invalid phone")
    private String phone;

    @NotNull(message = "age must be not null")
    @Min(value = 10, message = "age must be greater than 10")
    @Max(value = 22, message = "age must be lesser than 22")
    private Integer age;

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

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
}
