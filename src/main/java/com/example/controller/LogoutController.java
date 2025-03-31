package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LogoutController", value = "/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("user") != null) {
                session.invalidate();
                sendResponse(response, Messages.LOGOUT_SUCCESSFUL, null, null, 200);
            } else {
                sendResponse(response, Messages.Error.USER_NOT_FOUND, null, null, 400);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, ex.getMessage(), null, 500);
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
