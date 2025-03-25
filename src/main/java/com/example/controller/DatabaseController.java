package com.example.controller;

import com.example.common.Messages;
import com.example.config.DbConnect;
import com.example.common.exception.ApplicationException;
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
                throw new ApplicationException(Messages.Error.FAILED);
            }
            DbConnect.instance = DbConnect.getInstance(url, username, password, driver);
        } catch (ApplicationException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}