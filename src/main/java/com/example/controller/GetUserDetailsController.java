package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.Response;
import com.example.common.utils.SessionChecker;
import com.example.common.exception.ApplicationException;
import com.example.dto.UsersDTO;
import com.example.entityservice.UserServices;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "GetUserDetailsController", value = "/get-user")
public class GetUserDetailsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        UserServices userService = new UserServices();
        try {
            UsersDTO loggedUser = SessionChecker.checkSession(request);
            String id = request.getParameter("userId");
            UsersDTO user = userService.fetchUserDetailsById(id,loggedUser);
            sendResponse(response, null,null, user, 200);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(),null, null, 500);
        }
    }


    private void sendResponse(HttpServletResponse response, String message, String technicalMessage, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response();
        apiResponse.setMessage(message);
        apiResponse.setTechnicalMessage(technicalMessage);
        apiResponse.setData(data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
