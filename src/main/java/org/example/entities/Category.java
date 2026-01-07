package org.example.entities;

import org.example.constraints.annotation.Min;
import org.example.constraints.annotation.NotBlank;
import org.example.constraints.annotation.NotNull;

public class Category {
    @NotNull(message = "Category ID must not be null")
    @Min(value = 1, message = "Category ID must be at least 1")
    private int id;

    @NotBlank(message = "Category name must not be blank")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
