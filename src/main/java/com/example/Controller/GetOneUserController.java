package com.example.Controller;

import com.example.APIResponse;
import com.example.common.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.LoginRequestUserDTO;
import com.example.dto.UserDTO;
import com.example.exception.ApplicationException;
import com.example.model.User;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetOneUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        IUserDAO userdao = new UserDAOImpl();
        UserService userService = new UserService(userdao);

        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("user") != null) {
                LoginRequestUserDTO sessionUser = (LoginRequestUserDTO) session.getAttribute("user");
                List<UserDTO> list = userService.getOneUserDetails(sessionUser.getEmail());
                createResponse(response,Messages.FETCHED_USER,list,200);
            } else {
                throw new ApplicationException(Messages.Error.INVALID_ACTION, ApplicationException.ErrorType.USER_ERROR);
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            createResponse(response,Messages.Error.FAILED,null,500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void createResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
