package com.example.entityservice;

import com.example.common.Messages;
import com.example.common.enums.Role;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.entitymapper.UserMap;
import com.example.controller.validation.UserValidator;
import com.example.dao.entity.UserDAO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.User;

import java.util.List;

public class UserServices {

    private UserDAO userDAO = new UserDAO();

    public void addUser(UsersDTO userDTO) throws ApplicationException {
        try {
            UserValidator.validate(userDTO);
            if (userDAO.isUserEmailExists(userDTO.getEmail())) {
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
            User user = userDAO.loggedInUser(userDTO.getEmail(), userDTO.getPassword());
            if(user == null){
                throw new ApplicationException(Messages.Error.INVALID_CREDENTIALS);
            }
            return UserMap.toUserDTO(user);
        } catch (DBException ex) {
            throw ex;
        }
    }

    public UsersDTO fetchUserDetailsById(String id,UsersDTO user) throws ApplicationException {
        if (id == null || id.isBlank()) {
            throw new ApplicationException(Messages.Error.INVALID_USER_ID);
        }
        int Id = Integer.parseInt(id);
        if (Id <= 0) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        boolean isOwner = (Id == user.getUserId());
        boolean isAdmin = (user.getRole() != null && Role.ADMIN.equals(user.getRole()));
        if (!(isOwner || isAdmin)) {
            throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
        }
        UsersDTO userDTO = UserMap.toUserDTO(userDAO.fetchUserDetailsById(Id));
        return userDTO;
    }

    public List<UsersDTO> getAllUser() throws DBException {
        return UserMap.convertUserToUserDTOList(userDAO.getAllUser());
    }

    public void updateUserDetails(UsersDTO userDTO) throws ApplicationException {
        isValidUserId(userDTO.getUserId());
        User user = UserMap.forUpdateDTOToEntity(userDTO);
        userDAO.updateUserDetails(user);
    }

    public boolean isValidUserId(int id) throws ApplicationException {
        if (userDAO.isValidUserId(id) == false) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        return true;
    }

    public void updateUserActiveStatus(int userId, boolean status) throws ApplicationException {
        isValidUserId(userId);
        userDAO.updateUserActiveStatus(userId, status);
    }

    public boolean updatePassword(int userId, String password, String newPassword) throws ApplicationException {
        UserValidator.checkPassword(password, newPassword);
        isValidUserId(userId);
        if (userDAO.isValidCurrentPassword(userId, password) == false) {
            throw new ApplicationException(Messages.Error.INVALID_CURRENT_PASSWORD);
        }
        return userDAO.updatePassword(userId, newPassword);
    }

    public UsersDTO fetchUserDetailsById(int id) throws ApplicationException {
        return UserMap.toUserDTO(userDAO.fetchUserDetailsById(id));
    }

}
