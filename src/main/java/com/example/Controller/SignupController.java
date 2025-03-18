package com.example.Controller;

import com.example.APIResponse;
import com.example.common.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.SignupRequestUserDTO;
import com.example.model.User;
import com.example.exception.ApplicationException;
import com.example.service.UserService;
import com.example.validation.SignupValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        ObjectMapper mapper = new ObjectMapper();
        APIResponse apiResponse;
        try {
            SignupRequestUserDTO user = mapper.readValue(request.getReader(), SignupRequestUserDTO.class);
            SignupValidator.validate(user);
            userService.addUser(user);
//            HttpSession session = request.getSession();
//            session.setAttribute("user",user);
//            session.setMaxInactiveInterval(5*60);
            createResponse(response,Messages.ACCOUNT_CREATED,null,200);
        } catch (ApplicationException e) {
            e.printStackTrace();
             if(e.getErrorType() == ApplicationException.ErrorType.SYSTEM_ERROR) {
                 createResponse(response,e.getMessage(),null,500);
             }
            else{
                 createResponse(response,e.getMessage(),null,400);
             }
        }
    }

    private void createResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
