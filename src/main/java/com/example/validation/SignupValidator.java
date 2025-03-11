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
            throw new ApplicationException(Messages.Error.INVALIDVALUES);
        }

        if (!Validation.isValidPassword(user.getPasswords())) {
            throw new ApplicationException(Messages.Error.WEAKPASSWORD);
        }

        if (!Validation.isValidEmail(user.getEmail())) {
            throw new ApplicationException(Messages.Error.INVALIDEMAIL);
        }
        if (!Validation.isValidContact(user.getContact())) {
            throw new ApplicationException(Messages.Error.INVALIDCONTACT);
        }
        if (userDao.isUserExistByEmail(user.getEmail())) {
            throw new ApplicationException(Messages.Error.ALREADYEXISTS);
        }
        if(!Validation.isValidEmailLength(user.getEmail())){
            throw new ApplicationException(Messages.Error.INVALIDEMAILLENGTH);
        }
        if(!Validation.isValidFirstNameLength(user.getFirstName())){
            throw new ApplicationException(Messages.Error.INVALIDNAMELENGTH);
        }
        if(!Validation.isValidLastNameLength(user.getLastName())){
            throw new ApplicationException(Messages.Error.INVALIDNAMELENGTH);
        }
        if(!Validation.isValidPasswordLength(user.getPasswords())){
            throw new ApplicationException(Messages.Error.INVALIDPASSWORDLENGTH);
        }

    }
}
