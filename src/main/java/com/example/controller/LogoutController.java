package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("user") != null) {
                session.invalidate();
                sendResponse(response, Messages.LOGOUT_SUCCESSFUL, null, 200);
            } else {
                sendResponse(response, Messages.Error.USER_NOT_FOUND, null, 400);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, null, 500);
        }
    }

    private void sendResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response(message, data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }
}
