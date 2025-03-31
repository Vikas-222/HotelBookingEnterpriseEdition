package com.example.dao;

import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.dto.UserDTO;
import com.example.model.User;
import java.util.List;

public interface IUserDAO {

    boolean isUserExistByEmail(String emailId) throws DBException;

    void addUser(User user) throws DBException;

    boolean isValidUser(User user) throws DBException;

    User fetchLoggedInUserDetails(String email) throws DBException;

    List<User> getAllUser() throws DBException;

    void updateUserdetails(User user) throws DBException;

    void updateUserActiveStatus(int userId, boolean status) throws DBException;

    boolean isValidUserId(int Id) throws DBException;
}
