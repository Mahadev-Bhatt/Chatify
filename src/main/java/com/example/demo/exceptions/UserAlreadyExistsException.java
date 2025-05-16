package com.example.demo.exceptions;

// com.example.demo.exception.UserAlreadyExistsException.java
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
