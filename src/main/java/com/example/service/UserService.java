package com.example.service;

import com.example.common.mapper.UserMapper;
import com.example.dao.IUserDAO;
import com.example.dto.LoginRequestUserDTO;
import com.example.dto.SignupRequestUserDTO;
import com.example.dto.UserDTO;
import com.example.common.exception.ApplicationException;
import com.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final IUserDAO iUserDAO;

    public UserService(IUserDAO iUserDAO) {
        this.iUserDAO = iUserDAO;
    }

    public void addUser(SignupRequestUserDTO signupRequestUserDTO) throws ApplicationException {
        User user = UserMapper.userDTOToEntity(signupRequestUserDTO);
        iUserDAO.addUser(user);
    }

    public boolean isUserExists(String email) throws ApplicationException {
        return iUserDAO.isUserExistByEmail(email);
    }

    public void userLogin(LoginRequestUserDTO loginUser) throws ApplicationException {
        User user = UserMapper.loginUserDTOtoEntity(loginUser);
        iUserDAO.userLogin(user);
    }

    public List<UserDTO> getOneUserDetails(String email) throws ApplicationException {
        UserDTO userDTO = UserMapper.ToUserDTO(iUserDAO.getOneUserDetails(email));
        List<UserDTO> list = new ArrayList<>();
        list.add(userDTO);
        return list;
    }

    public List<UserDTO> getAllUser() throws ApplicationException {
        return UserMapper.entityToDTOList(iUserDAO.getAllUser());
    }

    public String getRole(String email) throws ApplicationException {
        return iUserDAO.getRoleOfUser(email);
    }
}
