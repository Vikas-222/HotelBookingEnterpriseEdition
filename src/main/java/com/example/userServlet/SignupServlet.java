package com.example.userServlet;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.Validation;
import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.exception.ApplicationException;
import com.example.validation.SignupValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


public class SignupServlet extends HttpServlet {

    UserDao userdao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        APIResponse apiResponse;
        try {

            User user = mapper.readValue(request.getReader(), User.class);
            SignupValidator.validate(user);

            userdao.addUser(user);
            apiResponse = new APIResponse(Messages.ACCOUNTCREATED);
            Response.responseMethod(response, 200, apiResponse);
            response.sendRedirect("home.html");

        } catch (ApplicationException e) {
            e.printStackTrace();
            Throwable cause = e.getCause();

            if (cause instanceof SQLException) {
                // Database error
                out.println("Database error: " + cause.getMessage());
                ((SQLException) cause).printStackTrace(); // Log the stack trace
            } else if (e.getMessage().equals(Messages.Error.USERNOTFOUND)) {
                // User not found
                out.println("User not found.");
            } else {
                // Other application exception
                out.println("An application error occurred: " + e.getMessage());
                e.printStackTrace();
            }
            apiResponse = new APIResponse(e.getMessage());
            Response.responseMethod(response, 400, apiResponse);
        } catch (Exception ex) {
            apiResponse = new APIResponse(ex.getMessage());
            Response.responseMethod(response, 500, apiResponse);
        }
    }
}
