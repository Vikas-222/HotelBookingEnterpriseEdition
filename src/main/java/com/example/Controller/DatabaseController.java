//package com.example.Controller;
//
//
//import com.example.common.Messages;
//import com.example.db.DbConnect;
//import com.example.exception.ApplicationException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DatabaseController extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String driver = getServletConfig().getInitParameter("driver");
//        String username = getServletConfig().getInitParameter("username");
//        String password = getServletConfig().getInitParameter("password");
//        String url = getServletConfig().getInitParameter("db_url");
//
//        try{
////            DbConnect.setValues(driver,url,username,password);
//            DbConnect dbConnect = new DbConnect(driver,url,username,password);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//            throw new ServletException(Messages.Error.FAILED);
//        }
//
//    }
//}
