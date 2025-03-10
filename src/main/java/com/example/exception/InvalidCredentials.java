package com.example.exception;

public class InvalidCredentials extends Exception{

    public InvalidCredentials(String message) {
        super(message);
    }
}
