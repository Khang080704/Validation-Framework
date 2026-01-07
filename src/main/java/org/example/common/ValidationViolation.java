package org.example.common;

import java.util.List;

public class ValidationViolation {
    private String path;
    private final List<String> messages;

    public ValidationViolation(String path, List<String> messages) {
        this.path = path;
        this.messages = messages;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getMessages() {
        return messages;
    }
}
