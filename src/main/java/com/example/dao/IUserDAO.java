package com.example.dao;

import com.example.exception.ApplicationException;
import com.example.model.User;
import java.util.List;

public interface IUserDAO {

    boolean isUserExistByEmail(String emailId) throws ApplicationException;

    void addUser(String fname,String email,String password,String contact) throws ApplicationException;

    void userLogin(String email, String password) throws ApplicationException;

    List<User> getOneUserDetails(int id) throws ApplicationException;

    List<User> getAllUser() throws ApplicationException;
}
