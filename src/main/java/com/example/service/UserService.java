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

    public void addUser(UserDTO userDTO) throws ApplicationException {
        try {
            UserValidator.validate(userDTO);
            if (isUserExists(userDTO.getEmail())) {
                throw new ApplicationException(Messages.Error.ALREADY_EXISTS);
            }
            User user = UserMapper.convertUserDTOToUserForSignup(userDTO);
            iUserDAO.addUser(user);
        }catch(DBException e){
            throw e;
        }
        catch (ApplicationException e) {
            throw e;
        }
    }

    public boolean isUserExists(String email) throws DBException {
        return iUserDAO.isUserExistByEmail(email);
    }

    public UserDTO userLogin(UserDTO userDTO) throws ApplicationException {
        try{
            User user = UserMapper.convertUserDTOToUserForLogin(userDTO);
            if(!iUserDAO.isUserExistByEmail(user.getEmail())){
                throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
            }
            if (iUserDAO.isValidUser(user) == false) {
                throw new ApplicationException(Messages.Error.INVALID_CREDENTIALS);
            }
            return fetchLoggedInUserDetails(userDTO.getEmail());
        } catch (DBException ex) {
            throw ex;
        }
    }

    public UserDTO fetchLoggedInUserDetails(String email) throws DBException {
        UserDTO userDTO = UserMapper.ToUserDTO(iUserDAO.fetchLoggedInUserDetails(email));
        return userDTO;
    }

    public List<UserDTO> getAllUser() throws DBException {
        return UserMapper.convertUserToUserDTOList(iUserDAO.getAllUser());
    }

    public void updateUserDetails(UserDTO userDTO) throws ApplicationException {
        User user = UserMapper.ForUpdateDTOToEntity(userDTO);
        if(iUserDAO.isUserExistByEmail(user.getEmail()) == false){
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        iUserDAO.updateUserdetails(user);
    }

    public void updateUserActiveStatus(int userId,boolean status) throws ApplicationException {
        if(!iUserDAO.isValidUserId(userId)){
            throw new ApplicationException(Messages.Error.INVALID_USERID);
        }
        iUserDAO.updateUserActiveStatus(userId, status);
    }

    public boolean isValidUserId(int id) throws DBException {
        return iUserDAO.isValidUserId(id);
    }

    public boolean updatePassword(int userId,String password,String newPassword) throws ApplicationException {
        UserValidator.checkPassword(password,newPassword);
        if(iUserDAO.isValidCurrentPassword(userId,password) == false){
            throw new ApplicationException(Messages.Error.INVALID_CURRENT_PASSWORD);
        }
        if(!iUserDAO.isValidUserId(userId)){
            throw new ApplicationException(Messages.Error.INVALID_USERID);
        }
        return iUserDAO.updatePassword(userId,newPassword);
    }
}
