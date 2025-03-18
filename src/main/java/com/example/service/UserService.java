package com.example.service;

import com.example.dao.IUserDAO;
import com.example.dto.LoginRequestUserDTO;
import com.example.dto.SignupRequestUserDTO;
import com.example.dto.UserDTO;
import com.example.exception.ApplicationException;
import com.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final IUserDAO iUserDAO;
    public UserService(IUserDAO iUserDAO){
        this.iUserDAO = iUserDAO;
    };
    private UserDTO userDTO = new UserDTO();

    public static User UserDTOToUser(SignupRequestUserDTO signupRequestUserDTO){
        String fname = signupRequestUserDTO.getFirstName();
        String lname = signupRequestUserDTO.getLastName();
        String email = signupRequestUserDTO.getEmail();
        String password = signupRequestUserDTO.getPassword();
        String contactNumber = signupRequestUserDTO.getContactNumber();
        return new User.UserBuilder(fname,lname,email,password,contactNumber).build();
    }

    public static User LoginUserDTOTOUser(LoginRequestUserDTO user){
        String email = user.getEmail();
        String password = user.getPassword();
        return new User.UserBuilder(email,password).build();
    }

    public static UserDTO UserToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setContactNumber(user.getContactNumber());
        userDTO.setGender(user.getGender());
        userDTO.setProfilePic(user.getProfilePic());
        return userDTO;
    }

    public void addUser(SignupRequestUserDTO signupRequestUserDTO) throws ApplicationException {
        User user = UserService.UserDTOToUser(signupRequestUserDTO);
        iUserDAO.addUser(user);
    }

    public boolean isUserExists(String email) throws ApplicationException {
        return iUserDAO.isUserExistByEmail(email);
    }

    public void userLogin(LoginRequestUserDTO loginUser) throws ApplicationException{
        User user = UserService.LoginUserDTOTOUser(loginUser);
        iUserDAO.userLogin(user);
    }

    public List<UserDTO>  getOneUserDetails(String email) throws ApplicationException{
        userDTO = UserService.UserToUserDTO(iUserDAO.getOneUserDetails(email));
        List<UserDTO> list = new ArrayList<>();
        list.add(userDTO);
        return list;
    }

    public List<User> getAllUser() throws ApplicationException{
        return iUserDAO.getAllUser();
    }
}
