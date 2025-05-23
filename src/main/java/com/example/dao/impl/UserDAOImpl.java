package com.example.dao.impl;

import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dao.IUserDAO;
import com.example.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements IUserDAO {

    @Override
    public boolean isUserEmailExists(String emailId) throws DBException {
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
                user = new User.UserBuilder()
                        .setUserId(rs.getInt("user_id"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name"))
                        .setEmail(rs.getString("email"))
                        .setContactNumber(rs.getString("contact"))
                        .setGender(rs.getString("gender"))
                        .setProfilePic(rs.getString("profilePic"))
                        .setIsActive(rs.getBoolean("is_active"))
                        .setRole(rs.getString("roles")).build();
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
            while (rs.next()) {
                User user = new User.UserBuilder()
                        .setUserId(rs.getInt("user_id"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name"))
                        .setEmail(rs.getString("email"))
                        .setContactNumber(rs.getString("contact"))
                        .setUserId(rs.getInt("user_id"))
                        .setGender(rs.getString("gender"))
                        .setIsActive(rs.getBoolean("is_active"))
                        .setRole(rs.getString("roles")).build();
                list.add(user);
            }
            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateUserdetails(User user) throws DBException {
        String updateQuery = "update user set last_name = ?, contact = ?, gender = ?,profilePic = ? where user_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(updateQuery);) {
            pstUpdate.setString(1, user.getLastName());
            pstUpdate.setString(2, user.getContactNumber());
            pstUpdate.setString(3, user.getGender());
            pstUpdate.setString(4, user.getProfilePic());
            pstUpdate.setInt(5, user.getUserId());
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateUserActiveStatus(int userId, boolean status) throws DBException {
        String updateQuery = "update user set is_active = ? where user_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(updateQuery);) {
            pstUpdate.setBoolean(1, status);
            pstUpdate.setInt(2, userId);
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean isValidUserId(int Id) throws DBException {
        String foundId = "select * from user where user_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(foundId)) {
            pst.setInt(1, Id);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean updatePassword(int userId, String password) throws DBException {
        String sql = "UPDATE user SET passwords = ? WHERE user_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, password);
            pst.setInt(2, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean isValidCurrentPassword(int userId, String password) throws DBException {
        String sql = "select passwords = ? from user WHERE user_id = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, password);
            pst.setInt(2, userId);
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

}
