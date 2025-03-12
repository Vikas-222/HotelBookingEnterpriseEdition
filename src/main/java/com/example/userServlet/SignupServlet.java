package com.example.userServlet;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.exception.ApplicationException;
import com.example.validation.SignupValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class SignupServlet extends HttpServlet {

    UserDao userdao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        APIResponse apiResponse;
        try {
            User user = mapper.readValue(request.getReader(), User.class);
            SignupValidator.validate(user);
            userdao.addUser(user);
            apiResponse = new APIResponse(Messages.ACCOUNT_CREATED);
            Response.responseMethod(response, 200, apiResponse);
        } catch (ApplicationException e) {
            e.printStackTrace();
             if(e.getErrorType() == ApplicationException.ErrorType.SYSTEM_ERROR) {
                apiResponse = new APIResponse(e.getMessage());
                Response.responseMethod(response, 500, apiResponse);
            }
            else{
                apiResponse = new APIResponse(e.getMessage());
                Response.responseMethod(response, 400, apiResponse);
            }
        }
    }
}
