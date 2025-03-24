package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.dao.IRoomDAO;
import com.example.dao.IUserDAO;
import com.example.dao.RoomDAOImpl;
import com.example.dao.UserDAOImpl;
import com.example.dto.RoomDTO;
import com.example.dto.UserDTO;
import com.example.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class UpdateUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        IUserDAO iUserDAO = new UserDAOImpl();
        UserService userService = new UserService(iUserDAO);
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            UserDTO user =(UserDTO) session.getAttribute("user");
            UserDTO.Builder userDTO = CustomObjectMapper.toObject(request.getReader(), UserDTO.Builder.class);
            userDTO.setUserId(user.getUserId());
            userService.updateUserDetails(userDTO.build());
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, null, 400);
        }
    }

    private void sendResponse(HttpServletResponse response, String message, String technicalMessage, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, technicalMessage, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}