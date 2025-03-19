package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.dto.UserDTO;
import com.example.common.exception.ApplicationException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class GetOneUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("user") != null) {
                UserDTO sessionUser = (UserDTO) session.getAttribute("user");
                sendResponse(response, null, sessionUser, 200);
            } else {
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, 500);
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
