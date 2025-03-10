package com.example.dao;

import com.example.common.Messages;
import com.example.db.DbConnect;
import com.example.entity.User;
import com.example.exception.InsertionFailException;
import com.example.exception.InvalidCredentials;
import com.example.exception.UserExistsException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Userdao {
    Connection connection = null;
    ResultSet rs;

    //    ---------------------------------------------------------------------------------------
    //User Registration method
    public boolean addUser(User user) throws SQLException, ClassNotFoundException, UserExistsException, InsertionFailException {
        connection = DbConnect.getConnection();

        String foundEmail = "select email from user where email = ?";               //to check email exists or not

        PreparedStatement pst = connection.prepareStatement(foundEmail);
        pst.setString(1, user.getEmail());
        rs = pst.executeQuery();
        if (rs.next()) {
            throw new UserExistsException(Messages.Error.ALREADYEXISTS);
        } else {
            String insertSql = "insert into user(first_name,email,passwords,contact) values (?,?,?,?)";        //insert after all validations

            PreparedStatement pst1 = connection.prepareStatement(insertSql);
            pst1.setString(1, user.getFirstName());
            pst1.setString(2, user.getEmail());
            pst1.setString(3, user.getPasswords());
            pst1.setString(4, user.getContact());

            int row = pst1.executeUpdate();

            if (row > 0) {
                return true;
            } else {
                throw new InsertionFailException(Messages.Error.FAILED);
            }
        }
    }


    //    --------------------------------------------------------------------------------------
    //login method
    public boolean userLogin(String email, String password) throws SQLException, ClassNotFoundException, InvalidCredentials {
        connection = DbConnect.getConnection();

        String foundEmail = "select email,passwords from user where email = ? and passwords =?";               //to check email exists or not
        PreparedStatement pst = connection.prepareStatement(foundEmail);
        pst.setString(1, email);
        pst.setString(2, password);
        rs = pst.executeQuery();
        if (rs.next()) {
            return true;
        } else {
            throw new InvalidCredentials(Messages.Error.INVALIDCREDENTIALS);
        }
    }


    //    ----------------------------------------------------------------------------------
    public List<User> getOneUserDetails(String email) throws SQLException, ClassNotFoundException, InvalidCredentials {
        connection = DbConnect.getConnection();
        String sql = "Select * from user where email = ?";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, email);
        rs = pst.executeQuery();
        List<User> list = new ArrayList<>();

        if (rs.next()) {
            while (rs.next()) {
                User user1 = new User.UserBuilder(rs.getString("first_name"), rs.getString("email"), rs.getString("passwords"), rs.getString("contact")).setLastName(rs.getString("last_name")).setGender(rs.getString("gender")).setIsActive(rs.getBoolean("is_active")).setRoles(rs.getString("roles")).build();
                list.add(user1);
            }
            return list;
        }
        else
            throw new InvalidCredentials(Messages.Error.USERNOTFOUND);

    }

    public List<User> getAllUser() throws Exception {
        ResultSet rs;

        connection = DbConnect.getConnection();
        String sql = "Select * from user";

        Statement st = connection.createStatement();
        rs = st.executeQuery(sql);
        List<User> list = new ArrayList<>();

        if (rs.next()) {
            while (rs.next()) {
                User user = new User.UserBuilder(rs.getString("first_name"), rs.getString("email"), rs.getString("passwords"), rs.getString("contact")).setLastName(rs.getString("last_name")).setGender(rs.getString("gender")).setIsActive(rs.getBoolean("is_active")).build();
                list.add(user);
            }
            return list;
        }
        else
            throw new Exception(Messages.Error.NOUSERFOUND);

    }
}
