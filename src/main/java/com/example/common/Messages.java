package com.example.common;

public class Messages {

    public static final String ACCOUNT_CREATED = "Account created successfully";
    public static final String LOGIN_SUCCESSFUL = "Login successful";
    public static final String LOGOUT_SUCCESSFUL = "Logout successful";
    public static final String ROOM_ADDED = "Room added successfully";

    public static class Error {
        public static final String ALREADY_EXISTS = "User already exists";
        public static final String INVALID_VALUES = "Please enter all the details";
        public static final String INVALID_EMAIL = "Please enter valid email address";
        public static final String FAILED = "OOPS! Something went wrong! Please try again after some time";
        public static final String WEAK_PASSWORD = "Please enter strong password";
        public static final String INVALID_CONTACT = "Please enter valid contact number";
        public static final String INVALID_CREDENTIALS = "Please enter valid credentials";
        public static final String UNAUTHORIZED_ACCESS = "You are unauthorized to access";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String NO_USER_EXISTS = "User record not exists";
        public static final String ERROR_OCCURED = "Sorry! Some error occured";
        public static final String INVALID_EMAIL_LENGTH = "Email is too big! Please use another email";
        public static final String INVALID_NAME_LENGTH = "Name is too big! Please use short names";
        public static final String INVALID_PASSWORD_LENGTH = "Password is too big! Please use other password";
        public static final String INVALID_DATABASE_CREDENTIALS = "Unable to connect database";
    }

    public static class RoomError{
        public static final String ROOM_EXISTS = "Sorry! Room number already exists";
        public static final String INVALID_CAPACITY = "Please enter valid room capacity";
        public static final String INVALID_ROOM_PRICE = "Please enter valid room price";
        public static final String INVALID_ROOM_NUMBER = "Please enter valid room number";
        public static final String INVALID_VALUES = "Please enter valid details";
    }

}
