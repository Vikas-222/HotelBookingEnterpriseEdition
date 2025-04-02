package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.utils.SessionValidator;
import com.example.dto.UserDTO;
import com.example.common.exception.ApplicationException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "GetLoggedUserController", value = "/get-user")
public class GetLoggedUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            UserDTO userDTO = SessionValidator.checkSession(request);
            sendResponse(response, null,null, userDTO, 200);
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
