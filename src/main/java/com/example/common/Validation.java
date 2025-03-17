package com.example.common;

import com.example.dto.SignupRequestUserDTO;
import com.example.model.User;
import com.example.exception.ApplicationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{6,15}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if(m.matches()){
            return true;
        }
        return false;
    }

    public static boolean isValidContact(String contact) {
        if (contact.length() != 10)
            return false;
        if (!contact.chars().allMatch(Character::isDigit)) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) throws ApplicationException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex);

        if (email == null) {
            return false;
        }
        if (!p.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public static boolean isNullCheckUserValues(SignupRequestUserDTO user) throws ApplicationException {
        if (user.getFirstName().trim().isEmpty() || user.getEmail().trim().isEmpty() || user.getPassword().trim().isEmpty()
                || user.getContactNumber().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isValidFirstNameLength(String fname) {
        if (fname.length() > 50) {
            return false;
        }
        return true;
    }

    public static boolean isValidLastNameLength(String lname) {
        if (lname.length() > 50) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmailLength(String email) {
        if (email.length() > 60) {
            return false;
        }
        return true;
    }

    public static boolean isValidPasswordLength(String password) {
        if (password.length() > 6 && password.length() < 15) {
            return true;
        }
        return false;
    }

    public static boolean isNullCheckLoginValues(String email,String password) throws ApplicationException {
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
