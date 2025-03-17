package com.example.Controller;

import com.example.APIResponse;
import com.example.common.CustomObjectMapper;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.exception.ApplicationException;
import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class HomePageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        APIResponse apiResponse;

        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String[] username = user.getEmail().split("@");
            out.println("<h1>Welcome " + username[0] + "</h1>");
        } else {
            createResponse(response,Messages.Error.INVALID_ACTION,null,400);
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
