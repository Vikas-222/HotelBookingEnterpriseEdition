package com.example.dao;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements IUserDAO {

    @Override
    public boolean isUserExistByEmail(String emailId) throws DBException {
        String foundEmail = "select email from user where email = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(foundEmail)) {
            pst.setString(1, emailId);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void addUser(User user) throws DBException {
        String insertSql = "insert into user(first_name,last_name,email,passwords,contact) values (?,?,?,?,?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst1 = connection.prepareStatement(insertSql)) {
            pst1.setString(1, user.getFirstName());
            pst1.setString(2, user.getLastName());
            pst1.setString(3, user.getEmail());
            pst1.setString(4, user.getPassword());
            pst1.setString(5, user.getContactNumber());
            pst1.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }


    @Override
    public boolean isValidUser(User user) throws DBException {
        ResultSet rs = null;
        String findEmail = "select email,passwords from user where email = ? and passwords =?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(findEmail)) {
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
        return false;
    }

    @Override
    public User fetchLoggedInUserDetails(String email) throws DBException {
        User user = null;
        String sql = "Select * from user where email = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()) {
                user = new User.UserBuilder().setUserId(rs.getInt("user_id")).setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name")).setEmail(rs.getString("email"))
                        .setContactNumber(rs.getString("contact"))
                        .setUserId(rs.getInt("user_id")).setGender(rs.getString("gender"))
                        .setIsActive(rs.getBoolean("is_active")).setRole(rs.getString("roles")).build();
            }
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
    }

    @Override
    public List<User> getAllUser() throws DBException {
        String sql = "Select * from user";
        try (Connection connection = DbConnect.instance.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<User> list = new ArrayList<>();
            if (rs.next()) {
                while (rs.next()) {
                    User user = new User.UserBuilder().setUserId(rs.getInt("user_id")).setFirstName(rs.getString("first_name"))
                            .setLastName(rs.getString("last_name")).setEmail(rs.getString("email"))
                            .setContactNumber(rs.getString("contact"))
                            .setUserId(rs.getInt("user_id")).setGender(rs.getString("gender"))
                            .setIsActive(rs.getBoolean("is_active")).setRole(rs.getString("roles")).build();
                    list.add(user);
                }
            }
            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateUserdetails(User user) throws DBException {
        String updateQuery = "update user set last_name = ?, contact = ?, gender = ? where user_id = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(updateQuery);) {
            pstUpdate.setString(1, user.getLastName());
            pstUpdate.setString(2, user.getContactNumber());
            pstUpdate.setString(3, user.getGender());
            pstUpdate.setInt(4, user.getUserId());
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}
