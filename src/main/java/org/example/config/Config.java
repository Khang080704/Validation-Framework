package org.example.config;

public class Config {
    private String message;

    public Config(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}