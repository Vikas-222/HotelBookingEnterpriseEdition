package com.example.userServlet;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.Validation;
import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.exception.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        UserDao userdao = new UserDao();
        APIResponse apiResponse;

        try {
            User user = mapper.readValue(request.getReader(), User.class);
            Validation.isNullCheckUserValues(user);
            userdao.userLogin(user.getEmail(), user.getPasswords());

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(60);
            session.setAttribute("user", user);
            apiResponse = new APIResponse(Messages.LOGIN_SUCCESSFUL);
            Response.responseMethod(response, 200, apiResponse);
        } catch (ApplicationException e) {
            e.printStackTrace();
            apiResponse = new APIResponse(Messages.Error.INVALID_CREDENTIALS);
            Response.responseMethod(response, 400, apiResponse);
        }
    }
}
