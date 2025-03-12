package com.example.dao;

import com.example.common.Messages;
import com.example.db.DbConnect;
import com.example.entity.User;
import com.example.exception.ApplicationException;

import java.sql.*;


public class UserDao {

    /**
     *
     * @param emailId
     * @return
     * @throws ApplicationException
     */
    public boolean isUserExistByEmail(String emailId) throws ApplicationException {
        try {
            Connection connection = DbConnect.getConnection();
            String foundEmail = "select email from user where email = ?";
            PreparedStatement pst = connection.prepareStatement(foundEmail);
            pst.setString(1, emailId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            throw new ApplicationException(Messages.Error.FAILED,ApplicationException.ErrorType.SYSTEM_ERROR,ex);
        }
        return false;
    }


    /**
     * add new user
     *
     * @param user
     * @throws ApplicationException
     */
    public void addUser(User user) throws ApplicationException {
        try {
            Connection connection = DbConnect.getConnection();
            String insertSql = "insert into user(first_name,email,passwords,contact) values (?,?,?,?)";
            PreparedStatement pst1 = connection.prepareStatement(insertSql);
            pst1.setString(1, user.getFirstName());
            pst1.setString(2, user.getEmail());
            pst1.setString(3, user.getPasswords());
            pst1.setString(4, user.getContact());
            pst1.executeUpdate();
        } catch (Exception ex) {
            throw new ApplicationException(Messages.Error.FAILED,ApplicationException.ErrorType.SYSTEM_ERROR,ex);
        }
    }


    /**
     *
     * @param email
     * @param password
     * @throws ApplicationException
     */

    public void userLogin(String email, String password) throws ApplicationException {
        try {
            Connection connection = DbConnect.getConnection();
            String foundEmail = "select email,passwords from user where email = ? and passwords =?";
            PreparedStatement pst = connection.prepareStatement(foundEmail);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                throw new ApplicationException(Messages.Error.USER_NOT_FOUND,ApplicationException.ErrorType.USER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(Messages.Error.FAILED,ApplicationException.ErrorType.SYSTEM_ERROR,e);
        }
    }

//    public List<User> getOneUserDetails(int id) throws SQLException, ClassNotFoundException, ApplicationException {
//        Connection connection = DbConnect.getConnection();
//        System.out.println(id);
//        String sql = "Select * from user where user_id = ?";
//
//        PreparedStatement pst = connection.prepareStatement(sql);
//        pst.setInt(1, id);
//        ResultSet rs = pst.executeQuery();
//        List<User> list = new ArrayList<>();
//
//        if (rs.next()) {
//            User user1 = new User.UserBuilder(rs.getString("first_name"), rs.getString("email"), rs.getString("passwords"), rs.getString("contact")).setUserId(rs.getInt("user_id")).setLastName(rs.getString("last_name")).setGender(rs.getString("gender")).setIsActive(rs.getBoolean("is_active")).setRoles(rs.getString("roles")).build();
//            list.add(user1);
//            return list;
//        } else
//            throw new ApplicationException(Messages.Error.USERNOTFOUND);
//
//    }
//
//    public List<User> getAllUser() throws Exception {
//        Connection connection = DbConnect.getConnection();
//        String sql = "Select * from user";
//
//        Statement st = connection.createStatement();
//        ResultSet rs = st.executeQuery(sql);
//        List<User> list = new ArrayList<>();
//
//        if (rs.next()) {
//            while (rs.next()) {
//                User user = new User.UserBuilder(rs.getString("first_name"), rs.getString("email"), rs.getString("passwords"), rs.getString("contact")).setLastName(rs.getString("last_name")).setGender(rs.getString("gender")).setIsActive(rs.getBoolean("is_active")).build();
//                list.add(user);
//            }
//            return list;
//        } else
//            throw new Exception(Messages.Error.NOUSERFOUND);
//
//    }
}
