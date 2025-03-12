package com.example.db;

import com.example.common.Messages;
import com.example.exception.ApplicationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    static Connection connection = null;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password123#";
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_db";

    public static Connection getConnection() throws ApplicationException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationException(Messages.Error.FAILED,ApplicationException.ErrorType.SYSTEM_ERROR);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ApplicationException(Messages.Error.FAILED,ApplicationException.ErrorType.SYSTEM_ERROR);
        }
    }
}
