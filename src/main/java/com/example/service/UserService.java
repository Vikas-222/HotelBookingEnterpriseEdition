package com.example.service;

import com.example.dao.IUserDAO;
import com.example.exception.ApplicationException;
import com.example.model.User;
import java.util.List;

public class UserService {

    private IUserDAO iUserDAO;
    private static UserService instance;
    private UserService(IUserDAO iUserDAO){
        this.iUserDAO = iUserDAO;
    };

    public void addUser(String fname,String email,String password,String contact) throws ApplicationException {
        iUserDAO.addUser(fname,email,password,contact);
    }

    public boolean isUserExists(String email) throws ApplicationException {
        return iUserDAO.isUserExistByEmail(email);
    }

    public void userLogin(String email,String password) throws ApplicationException{
        iUserDAO.userLogin(email,password);
    }

    public List<User> getOneUserDetails(int id) throws ApplicationException{
        return iUserDAO.getOneUserDetails(id);
    }

    public List<User> getAllUser() throws ApplicationException{
        return iUserDAO.getAllUser();
    }

    public static synchronized UserService getInstance(IUserDAO userDAO) {
        if (instance == null) {
            instance = new UserService(userDAO);
        }
        return instance;
    }

}
