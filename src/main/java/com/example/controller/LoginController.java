package com.example.controller;

import com.example.common.*;
import com.example.common.utils.CustomObjectMapper;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.common.exception.ApplicationException;
import com.example.dto.UserDTO;
import com.example.service.UserService;
import com.example.validation.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

public class LoginController extends HttpServlet {

    private IUserDAO iUserDAO = new UserDAOImpl();
    private UserService userService = new UserService(iUserDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        try {
            UserDTO user = mapper.readValue(request.getReader(),UserDTO.class);
            UserValidator.isNullCheckLoginValues(user.getEmail(),user.getPassword());
            UserDTO userDTO = userService.userLogin(user);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(5*60);
            session.setAttribute("user", userDTO);
            sendResponse(response,Messages.LOGIN_SUCCESSFUL,null,null,200);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response,Messages.Error.INVALID_CREDENTIALS,e.getMessage(),null,400);
        }
    }

    private void sendResponse(HttpServletResponse response, String message,String technicalMessage, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message,technicalMessage, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
