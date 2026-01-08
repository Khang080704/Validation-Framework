package org.example.entities;

import org.example.constraints.annotation.*;

public class Product {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    @Min(value = 0, message = "Quantity must be at least 0")
    @Max(value = 10000, message = "Quantity must not exceed 10000")
    private int quantity;

    @IsValid
    private Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
