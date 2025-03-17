package com.example.validation;

import com.example.common.Messages;
import com.example.common.Validation;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.SignupRequestUserDTO;
import com.example.model.User;
import com.example.exception.ApplicationException;
import com.example.service.UserService;

public class SignupValidator {

    public static void validate(SignupRequestUserDTO user) throws ApplicationException {
        IUserDAO userDao = new UserDAOImpl();
        UserService userService = new UserService(userDao);

        if (!Validation.isNullCheckUserValues(user)) {
            throw new ApplicationException(Messages.Error.INVALID_VALUES, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidPassword(user.getPassword())) {
            throw new ApplicationException(Messages.Error.WEAK_PASSWORD, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidEmail(user.getEmail())) {
            throw new ApplicationException(Messages.Error.INVALID_EMAIL, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidContact(user.getContactNumber())) {
            throw new ApplicationException(Messages.Error.INVALID_CONTACT, ApplicationException.ErrorType.USER_ERROR);
        }
        if (userService.isUserExists(user.getEmail())) {
            throw new ApplicationException(Messages.Error.ALREADY_EXISTS, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidEmailLength(user.getEmail())) {
            throw new ApplicationException(Messages.Error.INVALID_EMAIL_LENGTH, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidFirstNameLength(user.getFirstName())) {
            throw new ApplicationException(Messages.Error.INVALID_NAME_LENGTH, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidLastNameLength(user.getLastName())) {
            throw new ApplicationException(Messages.Error.INVALID_NAME_LENGTH, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidPasswordLength(user.getPassword())) {
            throw new ApplicationException(Messages.Error.INVALID_PASSWORD_LENGTH, ApplicationException.ErrorType.USER_ERROR);
        }

    }
}
