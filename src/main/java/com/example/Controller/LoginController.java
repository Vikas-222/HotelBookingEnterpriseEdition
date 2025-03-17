package com.example.Controller;

import com.example.APIResponse;
import com.example.common.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.Validation;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.model.User;
import com.example.exception.ApplicationException;
import com.example.service.UserService;
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
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        APIResponse apiResponse;

        try {
            User user = mapper.readValue(request.getReader(), User.class);
            Validation.isNullCheckLoginValues(user.getEmail(),user.getPassword());
            userService.userLogin(user.getEmail(), user.getPassword());
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(5*60);
            session.setAttribute("user", user);
            createResponse(response,Messages.LOGIN_SUCCESSFUL,null,200);
        } catch (ApplicationException e) {
            e.printStackTrace();
            createResponse(response,Messages.Error.INVALID_CREDENTIALS,null,400);
        }
    }

    private void createResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
