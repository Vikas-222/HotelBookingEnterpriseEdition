package com.example.Controller;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
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
    private UserService userService = UserService.getInstance(iUserDAO);

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
            userService.addUser(user.getFirstName(),user.getEmail(),user.getPassword(),user.getContact());
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(5*60);
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
