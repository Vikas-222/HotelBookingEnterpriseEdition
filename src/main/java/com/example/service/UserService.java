package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.UserMapper;
import com.example.dao.IUserDAO;
import com.example.dto.UserDTO;
import com.example.model.User;

import java.util.List;

public class UserService {

    private final IUserDAO iUserDAO;

    public UserService(IUserDAO iUserDAO) {
        this.iUserDAO = iUserDAO;
    }

    public void addUser(UserDTO userDTO) throws DBException {
        User user = UserMapper.convertUserDTOToUserForSignup(userDTO);
        iUserDAO.addUser(user);
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
        boolean flag = iUserDAO.isUserExistByEmail(user.getEmail());
        if(!flag){
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        iUserDAO.updateUserdetails(user);
    }
}
