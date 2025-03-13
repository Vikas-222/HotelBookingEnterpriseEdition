package com.example.db;

import com.example.common.Messages;
import com.example.exception.ApplicationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    static Connection connection = null;
//    private static String Dbusername;
//    private static String Dbpassword;
//    private static String Dburl;
//    private static String Dbdriver;
//
//    public DbConnect(String driver, String url, String username, String password){
//        Dbdriver = driver;
//        Dburl = url;
//        Dbusername = username;
//        Dbpassword = password;
//    }

    public static Connection getConnection() throws ApplicationException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db","root","password123#");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new ApplicationException(Messages.Error.FAILED,ApplicationException.ErrorType.SYSTEM_ERROR);
        }
    }
}
