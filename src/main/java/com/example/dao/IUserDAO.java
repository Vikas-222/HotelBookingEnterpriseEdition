package com.example.dao;

import com.example.exception.ApplicationException;
import com.example.model.User;
import java.util.List;

public interface IUserDAO {

    boolean isUserExistByEmail(String emailId) throws ApplicationException;

    void addUser(User user) throws ApplicationException;

    void userLogin(String email, String password) throws ApplicationException;

    List<User> getOneUserDetails(String email) throws ApplicationException;

    List<User> getAllUser() throws ApplicationException;
}
