package com.example.controller;

import com.example.common.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.LoginRequestUserDTO;
import com.example.dto.UserDTO;
import com.example.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class GetAllUserController extends HttpServlet {
    IUserDAO userdao = new UserDAOImpl();
    UserService userService = new UserService(userdao);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("user") != null) {
                LoginRequestUserDTO user = (LoginRequestUserDTO) request.getAttribute("user");
                if (userService.getRole(user.getEmail()) == "admin") {
                    List<UserDTO> userList = userService.getAllUser();
                    if (userList == null) {
                        sendResponse(response, Messages.Error.NO_USER_EXISTS, null, 400);
                    } else {
                        sendResponse(response, Messages.FETCHED_USER, userList, 200);
                    }
                }
            } else {
                sendResponse(response, Messages.Error.INVALID_ACTION, null, 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, null, 500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
