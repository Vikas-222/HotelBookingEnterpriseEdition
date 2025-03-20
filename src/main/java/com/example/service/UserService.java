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
        try {
            User user = UserMapper.convertUserDTOToUserForLogin(userDTO);
            if (iUserDAO.isValidUser(user) == false) {
                throw new DBException(Messages.Error.INVALID_CREDENTIALS);
            }
            return getOneUserDetails(userDTO.getEmail());
        } catch (DBException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException("Exception while performing login:", ex);
        }
    }

    public UserDTO getOneUserDetails(String email) throws DBException {
        UserDTO userDTO = UserMapper.ToUserDTO(iUserDAO.getOneUserDetails(email));
        return userDTO;
    }

    public List<UserDTO> getAllUser() throws DBException {
        return UserMapper.convertUserToUserDTOList(iUserDAO.getAllUser());
    }
}
