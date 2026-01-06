package org.example.constraints.annotation;

public @interface NotBlank {
    String message() default "";
}
