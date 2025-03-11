package com.example.common;

import com.example.entity.User;
import com.example.exception.ApplicationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isValidContact(String contact) {
        if (contact.length() != 10 || !contact.chars().allMatch(Character::isDigit)) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex);

        if (email == null && !p.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public static boolean isNullCheckUserValues(User user) throws ApplicationException {
        if (user.getFirstName() == null || user.getEmail() == null || user.getPasswords() == null || user.getContact() == null
                || user.getFirstName().trim().isEmpty() || user.getEmail().trim().isEmpty() || user.getPasswords().trim().isEmpty()
                || user.getContact().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isValidFirstNameLength(String fname){
        if(fname.length() > 50){
            return false;
        }
        return true;
    }
    public static boolean isValidLastNameLength(String lname){
        if(lname.length() > 50){
            return false;
        }
        return true;
    }
    public static boolean isValidEmailLength(String email){
        if(email.length() > 60){
            return false;
        }
        return true;
    }

    public static boolean isValidPasswordLength(String password){
        if(password.length() > 20){
            return false;
        }
        return true;
    }


}
