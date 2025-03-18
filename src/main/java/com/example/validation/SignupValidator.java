package com.example.validation;

import com.example.common.Messages;
import com.example.common.UserValidation;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.SignupRequestUserDTO;
import com.example.common.exception.ApplicationException;
import com.example.service.UserService;

public class SignupValidator {

    public static void validate(SignupRequestUserDTO user) throws ApplicationException {
        IUserDAO userDao = new UserDAOImpl();
        UserService userService = new UserService(userDao);

        if (!UserValidation.isNullCheckUserValues(user)) {
            throw new ApplicationException(Messages.Error.INVALID_VALUES, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!UserValidation.isValidPassword(user.getPassword())) {
            throw new ApplicationException(Messages.Error.WEAK_PASSWORD, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!UserValidation.isValidEmail(user.getEmail())) {
            throw new ApplicationException(Messages.Error.INVALID_EMAIL, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!UserValidation.isValidContact(user.getContactNumber())) {
            throw new ApplicationException(Messages.Error.INVALID_CONTACT, ApplicationException.ErrorType.USER_ERROR);
        }
        if (userService.isUserExists(user.getEmail())) {
            throw new ApplicationException(Messages.Error.ALREADY_EXISTS, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!UserValidation.isValidEmailLength(user.getEmail())) {
            throw new ApplicationException(Messages.Error.INVALID_EMAIL_LENGTH, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!UserValidation.isValidFirstNameLength(user.getFirstName())) {
            throw new ApplicationException(Messages.Error.INVALID_NAME_LENGTH, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!UserValidation.isValidLastNameLength(user.getLastName())) {
            throw new ApplicationException(Messages.Error.INVALID_NAME_LENGTH, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!UserValidation.isValidPasswordLength(user.getPassword())) {
            throw new ApplicationException(Messages.Error.INVALID_PASSWORD_LENGTH, ApplicationException.ErrorType.USER_ERROR);
        }

    }
}
