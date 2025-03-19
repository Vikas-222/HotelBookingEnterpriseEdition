package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
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
        response.setContentType(AppConstant.APPLICATION_JSON);

        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("user") == null) {
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (!user.getRole().equalsIgnoreCase("admin")) {
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            List<UserDTO> userList = userService.getAllUser();
            if (userList == null) {
                sendResponse(response, Messages.Error.NO_USER_EXISTS, null, 200);
            }
            sendResponse(response, null, userList, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response,e.getMessage(),null,500);
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
