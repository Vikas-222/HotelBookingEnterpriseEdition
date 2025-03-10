package com.example.exception;

public class UserExistsException extends Exception{

    public UserExistsException(String message) {
        super(message);
    }
}
