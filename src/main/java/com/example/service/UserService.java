package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.UserMapper;
import com.example.dao.IUserDAO;
import com.example.dao.impl.UserDAOImpl;
import com.example.dto.UserDTO;
import com.example.model.User;
import com.example.controller.validation.UserValidator;

import java.util.List;

public class UserService {

    private IUserDAO iUserDAO = new UserDAOImpl();


    public UserDTO userLogin(UserDTO userDTO) throws ApplicationException {
        try {
            User user = UserMapper.convertUserDTOToUserForLogin(userDTO);
            iUserDAO.isUserEmailExists(user.getEmail());
            if (iUserDAO.isValidUser(user) == false) {
                throw new ApplicationException(Messages.Error.INVALID_CREDENTIALS);
            }
            return fetchLoggedInUserDetails(user.getEmail());
        } catch (DBException ex) {
            throw ex;
        }
    }

    public UserDTO fetchLoggedInUserDetails(String email) throws DBException {
        UserDTO userDTO = UserMapper.toUserDTO(iUserDAO.fetchLoggedInUserDetails(email));
        return userDTO;
    }

    public List<UserDTO> getAllUser() throws DBException {
        return UserMapper.convertUserToUserDTOList(iUserDAO.getAllUser());
    }

    public void updateUserDetails(UserDTO userDTO) throws ApplicationException {
        if (iUserDAO.isValidUserId(userDTO.getUserId()) == false) {
            throw new ApplicationException(Messages.Error.INVALID_USER_ID);
        }
        User user = UserMapper.forUpdateDTOToEntity(userDTO);
        iUserDAO.updateUserdetails(user);
    }

    public void updateUserActiveStatus(int userId, boolean status) throws ApplicationException {
        if (!iUserDAO.isValidUserId(userId)) {
            throw new ApplicationException(Messages.Error.INVALID_USER_ID);
        }
        iUserDAO.updateUserActiveStatus(userId, status);
    }

    public boolean isValidUserId(int id) throws ApplicationException {
        if (iUserDAO.isValidUserId(id) == false) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        return true;
    }

    public boolean updatePassword(int userId, String password, String newPassword) throws ApplicationException {
        UserValidator.checkPassword(password, newPassword);
        if (iUserDAO.isValidCurrentPassword(userId, password) == false) {
            throw new ApplicationException(Messages.Error.INVALID_CURRENT_PASSWORD);
        }
        if (!iUserDAO.isValidUserId(userId)) {
            throw new ApplicationException(Messages.Error.INVALID_USER_ID);
        }
        return iUserDAO.updatePassword(userId, newPassword);
    }
}
