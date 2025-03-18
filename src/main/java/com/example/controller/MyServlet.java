package com.example.controller;

import com.example.common.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.UserValidation;
import com.example.common.exception.ApplicationException;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.LoginRequestUserDTO;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

public class MyServlet extends HttpServlet {

    private IUserDAO iUserDAO = new UserDAOImpl();
    private UserService userService = new UserService(iUserDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginRequestUserDTO loginUser = mapper.readValue(request.getReader(),LoginRequestUserDTO.class);
            UserValidation.isNullCheckLoginValues(loginUser.getEmail(),loginUser.getPassword());
            userService.userLogin(loginUser);
//            HttpSession session = request.getSession();
//            session.setMaxInactiveInterval(5*60);
//            session.setAttribute("user", loginUser);
            sendResponse(response, Messages.LOGIN_SUCCESSFUL,null,200);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response,Messages.Error.INVALID_CREDENTIALS,null,400);
        }
    }

    private void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
