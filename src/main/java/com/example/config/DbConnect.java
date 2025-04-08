package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    public static DbConnect instance = null;

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;
    private final String dbDriver;

    private DbConnect(String url, String username, String password, String driver) {
        dbUrl = url;
        dbUsername = username;
        dbPassword = password;
        dbDriver = driver;
    }

    public static DbConnect getInstance(String url, String username, String password, String driver) {
        if (instance == null) {
            instance = new DbConnect(url, username, password, driver);
        }
        return instance;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(dbDriver);
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}
