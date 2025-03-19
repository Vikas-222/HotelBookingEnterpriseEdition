package com.example.service;

import com.example.common.exception.DBException;
import com.example.common.mapper.UserMapper;
import com.example.dao.IUserDAO;
import com.example.dto.UserDTO;
import com.example.common.exception.ApplicationException;
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

    public UserDTO userLogin(UserDTO userDTO) throws DBException {
        User user = UserMapper.convertUserDTOToUserForLogin(userDTO);
        iUserDAO.isValidUser(user);
        return getOneUserDetails(userDTO.getEmail());
    }

    public UserDTO getOneUserDetails(String email) throws DBException {
        UserDTO userDTO = UserMapper.ToUserDTO(iUserDAO.getOneUserDetails(email));
        return userDTO;
    }

    public List<UserDTO> getAllUser() throws DBException {
        return UserMapper.convertUserToUserDTOList(iUserDAO.getAllUser());
    }
}
