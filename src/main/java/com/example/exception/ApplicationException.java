package com.example.exception;

public class ApplicationException extends Exception {

    public enum ErrorType {
        USER_ERROR,
        SYSTEM_ERROR
    }

    private ErrorType errorType;

    public ApplicationException(String message,ErrorType error){
        super(message);
        errorType = error;
    }

    public ApplicationException(String message, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
