package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.UserMap;
import com.example.common.mapper.UserMapper;
import com.example.controller.validation.UserValidator;
import com.example.dao.impl.UserDAO;
import com.example.dto.UserDTO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.User;

import java.util.List;

public class UserServices {

    private UserDAO userDAO = new UserDAO();

    public void addUser(UsersDTO userDTO) throws ApplicationException {
        try {
            UserValidator.validate(userDTO);
            if (userDAO.isUserEmailExists(userDTO.getEmail()) == true) {
                throw new ApplicationException(Messages.Error.ALREADY_EXISTS);
            }
            User user = UserMap.convertUserDTOToUserForSignup(userDTO);
            userDAO.addUser(user);
        } catch (DBException e) {
            throw e;
        } catch (ApplicationException e) {
            throw e;
        }
    }

    public UsersDTO userLogin(UsersDTO userDTO) throws ApplicationException {
        try {
            User user = UserMap.convertUserDTOToUserForLogin(userDTO);
            if (userDAO.isValidUser(user) == false) {
                throw new ApplicationException(Messages.Error.INVALID_CREDENTIALS);
            }
            return fetchLoggedInUserDetails(user.getEmail());
        } catch (DBException ex) {
            throw ex;
        }
    }

    public UsersDTO fetchLoggedInUserDetails(String email) throws DBException {
        UsersDTO userDTO = UserMap.toUserDTO(userDAO.fetchLoggedInUserDetails(email));
        return userDTO;
    }

    public List<UsersDTO> getAllUser() throws DBException {
        return UserMap.convertUserToUserDTOList(userDAO.getAllUser());
    }


}
