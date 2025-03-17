package com.example.Controller;

import com.example.common.Messages;
import com.example.db.DbConnect;
import com.example.exception.ApplicationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DatabaseController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            String driver = getServletConfig().getInitParameter("driver");
            String username = getServletConfig().getInitParameter("username");
            String password = getServletConfig().getInitParameter("password");
            String url = getServletConfig().getInitParameter("db_url");

            if (url == null || username == null || password == null || driver == null) {
                throw new ApplicationException(Messages.Error.FAILED, ApplicationException.ErrorType.SYSTEM_ERROR);
            }
            DbConnect.instance = DbConnect.getInstance(url, username, password, driver);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}