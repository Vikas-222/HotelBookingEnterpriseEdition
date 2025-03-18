package com.example.controller;

import com.example.common.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.SignupRequestUserDTO;
import com.example.common.exception.ApplicationException;
import com.example.service.UserService;
import com.example.validation.SignupValidator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class SignupController extends HttpServlet {

    private IUserDAO iUserDAO = new UserDAOImpl();
    private UserService userService = new UserService(iUserDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            SignupRequestUserDTO user = CustomObjectMapper.toObject(request.getReader(),SignupRequestUserDTO.class);
            SignupValidator.validate(user);
            userService.addUser(user);
            sendResponse(response,Messages.ACCOUNT_CREATED,null,200);
        } catch (ApplicationException e) {
            e.printStackTrace();
             if(e.getErrorType() == ApplicationException.ErrorType.SYSTEM_ERROR) {
                 sendResponse(response,e.getMessage(),null,500);
             }
            else{
                 sendResponse(response,e.getMessage(),null,400);
             }
        }
    }

    private void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
