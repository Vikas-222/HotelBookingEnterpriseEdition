package com.example.Controller;

import com.example.APIResponse;
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
            Validation.isNullCheckLoginValues(user.getEmail(),user.getPassword());
            userService.userLogin(user.getEmail(), user.getPassword());

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(5*60);
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
