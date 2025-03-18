package com.example.dao;

import com.example.common.Messages;
import com.example.db.DbConnect;
import com.example.model.User;
import com.example.common.exception.ApplicationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements IUserDAO {

    /**
     * @param emailId
     * @return
     * @throws ApplicationException
     */
    @Override
    public boolean isUserExistByEmail(String emailId) throws ApplicationException {
        try (Connection connection = DbConnect.instance.getConnection()) {
            String foundEmail = "select email from user where email = ?";
            PreparedStatement pst = connection.prepareStatement(foundEmail);
            pst.setString(1, emailId);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            throw new ApplicationException(Messages.Error.FAILED, ApplicationException.ErrorType.SYSTEM_ERROR, ex);
        }
    }

    @Override
    public void addUser(User user) throws ApplicationException {
        try (Connection connection = DbConnect.instance.getConnection()) {
            String insertSql = "insert into user(first_name,last_name,email,passwords,contact) values (?,?,?,?,?)";
            PreparedStatement pst1 = connection.prepareStatement(insertSql);
            pst1.setString(1, user.getFirstName());
            pst1.setString(2, user.getLastName());
            pst1.setString(3, user.getEmail());
            pst1.setString(4, user.getPassword());
            pst1.setString(5, user.getContactNumber());
            pst1.executeUpdate();
        } catch (Exception ex) {
            throw new ApplicationException(Messages.Error.FAILED, ApplicationException.ErrorType.SYSTEM_ERROR, ex);
        }
    }


    @Override
    public void userLogin(User user) throws ApplicationException {
        try (Connection connection = DbConnect.instance.getConnection()) {
            String foundEmail = "select email,passwords from user where email = ? and passwords =?";
            PreparedStatement pst = connection.prepareStatement(foundEmail);
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                throw new ApplicationException(Messages.Error.USER_NOT_FOUND, ApplicationException.ErrorType.USER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new ApplicationException(Messages.Error.FAILED, ApplicationException.ErrorType.SYSTEM_ERROR, e);
        }
    }

    @Override
    public User getOneUserDetails(String email) throws ApplicationException {
        User user = null;
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection()) {
            String sql = "Select * from user where email = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()) {
                user = new User.UserBuilder(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("passwords"), rs.getString("contact")).setUserId(rs.getInt("user_id")).setGender(rs.getString("gender")).setIsActive(rs.getBoolean("is_active")).setRoles(rs.getString("roles")).build();
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(Messages.Error.FAILED, ApplicationException.ErrorType.SYSTEM_ERROR, e);
        }
    }

    @Override
    public List<User> getAllUser() throws ApplicationException {
        try (Connection connection = DbConnect.instance.getConnection()) {
            String sql = "Select * from user";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<User> list = new ArrayList<>();
            if (rs.next()) {
                while (rs.next()) {
                    User user = new User.UserBuilder(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("passwords"), rs.getString("contact")).setGender(rs.getString("gender")).setIsActive(rs.getBoolean("is_active")).build();
                    list.add(user);
                }
                return list;
            } else {
                throw new ApplicationException(Messages.Error.NO_USER_EXISTS, ApplicationException.ErrorType.SYSTEM_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(Messages.Error.FAILED, ApplicationException.ErrorType.SYSTEM_ERROR, e);
        }
    }

    public String getRoleOfUser(String email) throws ApplicationException {
        try (Connection connection = DbConnect.instance.getConnection()) {
            String sql = "Select roles from user where email = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("roles");
            }else{
                throw new ApplicationException(Messages.Error.USER_NOT_FOUND,ApplicationException.ErrorType.USER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(Messages.Error.FAILED, ApplicationException.ErrorType.SYSTEM_ERROR, e);
        }
    }
}
