package com.example.validation;

import com.example.common.Messages;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.common.exception.ApplicationException;
import com.example.dto.UserDTO;
import com.example.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public static void validate(UserDTO user) throws ApplicationException {
        IUserDAO userDao = new UserDAOImpl();
        UserService userService = new UserService(userDao);

        if (!isNullCheckUserValues(user)) {
            throw new ApplicationException(Messages.Error.INVALID_VALUES);
        }
        if (!isValidPassword(user.getPassword())) {
            throw new ApplicationException(Messages.Error.WEAK_PASSWORD);
        }
        if (!isValidEmail(user.getEmail())) {
            throw new ApplicationException(Messages.Error.INVALID_EMAIL);
        }
        if (!isValidContact(user.getContactNumber())) {
            throw new ApplicationException(Messages.Error.INVALID_CONTACT);
        }
        if (userService.isUserExists(user.getEmail())) {
            throw new ApplicationException(Messages.Error.ALREADY_EXISTS);
        }
        if (!isValidEmailLength(user.getEmail())) {
            throw new ApplicationException(Messages.Error.INVALID_EMAIL_LENGTH);
        }
        if (!isValidFirstNameLength(user.getFirstName())) {
            throw new ApplicationException(Messages.Error.INVALID_NAME_LENGTH);
        }
        if (!isValidLastNameLength(user.getLastName())) {
            throw new ApplicationException(Messages.Error.INVALID_NAME_LENGTH);
        }
        if (!isValidPasswordLength(user.getPassword())) {
            throw new ApplicationException(Messages.Error.INVALID_PASSWORD_LENGTH);
        }

    }

    public static boolean isNullCheckUserValues(UserDTO user) throws ApplicationException {
        return !user.getFirstName().isBlank() && !user.getEmail().isBlank() && !user.getPassword().isBlank()
                && !user.getContactNumber().isBlank();
    }

    public static boolean isValidFirstNameLength(String fname) {
        return fname.length() <= 50;
    }

    public static boolean isValidLastNameLength(String lname) {
        return lname.length() <= 50;
    }

    public static boolean isValidEmailLength(String email) {
        return email.length() <= 60;
    }

    public static boolean isValidPasswordLength(String password) {
        return password.length() > 6 && password.length() < 15;
    }

    public static boolean isNullCheckLoginValues(String email,String password) throws ApplicationException {
        return !email.isBlank() && !password.isBlank();
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{6,15}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isValidContact(String contact) {
        if (contact.length() != 10)
            return false;
        return contact.chars().allMatch(Character::isDigit);
    }

    public static boolean isValidEmail(String email) throws ApplicationException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex);

        if (email == null) {
            return false;
        }
        return p.matcher(email).matches();
    }
}
