package com.example.validation;

import com.example.common.Messages;
import com.example.common.Validation;
import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.exception.ApplicationException;

public class SignupValidator {

    private static UserDao userDao = new UserDao();

    public static void validate(User user) throws ApplicationException {
        if(!Validation.isNullCheckUserValues(user)){
            throw new ApplicationException(Messages.Error.INVALID_VALUES,ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidPassword(user.getPasswords())) {
            throw new ApplicationException(Messages.Error.WEAK_PASSWORD,ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidEmail(user.getEmail())) {
            throw new ApplicationException(Messages.Error.INVALID_EMAIL,ApplicationException.ErrorType.USER_ERROR);
        }
        if (!Validation.isValidContact(user.getContact())) {
            throw new ApplicationException(Messages.Error.INVALID_CONTACT,ApplicationException.ErrorType.USER_ERROR);
        }
        if (userDao.isUserExistByEmail(user.getEmail())) {
            throw new ApplicationException(Messages.Error.ALREADY_EXISTS,ApplicationException.ErrorType.USER_ERROR);
        }
        if(!Validation.isValidEmailLength(user.getEmail())){
            throw new ApplicationException(Messages.Error.INVALID_EMAIL_LENGTH,ApplicationException.ErrorType.USER_ERROR);
        }
        if(!Validation.isValidFirstNameLength(user.getFirstName())){
            throw new ApplicationException(Messages.Error.INVALID_NAME_LENGTH,ApplicationException.ErrorType.USER_ERROR);
        }
        if(!Validation.isValidPasswordLength(user.getPasswords())){
            throw new ApplicationException(Messages.Error.INVALID_PASSWORD_LENGTH,ApplicationException.ErrorType.USER_ERROR);
        }

    }
}
