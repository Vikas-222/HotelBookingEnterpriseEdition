package com.example.dao;

import com.example.common.exception.ApplicationException;
import com.example.model.User;
import java.util.List;

public interface IUserDAO {

    boolean isUserExistByEmail(String emailId) throws ApplicationException;

    void addUser(User user) throws ApplicationException;

    void userLogin(User user) throws ApplicationException;

    User getOneUserDetails(String email) throws ApplicationException;

    String getRoleOfUser(String email) throws ApplicationException;

    List<User> getAllUser() throws ApplicationException;
}
