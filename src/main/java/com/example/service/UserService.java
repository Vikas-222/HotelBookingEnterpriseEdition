package com.example.service;

import com.example.dao.IUserDAO;
import com.example.dto.LoginRequestUserDTO;
import com.example.dto.SignupRequestUserDTO;
import com.example.exception.ApplicationException;
import com.example.model.User;
import java.util.List;

public class UserService {

    private final IUserDAO iUserDAO;
    public UserService(IUserDAO iUserDAO){
        this.iUserDAO = iUserDAO;
    };

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

    public void addUser(SignupRequestUserDTO signupRequestUserDTO) throws ApplicationException {
        User user = UserService.UserDTOToUser(signupRequestUserDTO);
        iUserDAO.addUser(user);
    }

    public boolean isUserExists(String email) throws ApplicationException {
        return iUserDAO.isUserExistByEmail(email);
    }

    public void userLogin(String email,String password) throws ApplicationException{
        iUserDAO.userLogin(email,password);
    }

    public List<User> getOneUserDetails(String email) throws ApplicationException{
        return iUserDAO.getOneUserDetails(email);
    }

    public List<User> getAllUser() throws ApplicationException{
        return iUserDAO.getAllUser();
    }
}
