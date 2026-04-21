package com.tp39;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) { super(message); }
}