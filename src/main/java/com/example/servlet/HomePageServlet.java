package com.example.servlet;


import com.example.APIResponse;
import com.example.common.Messages;
import com.example.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class HomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        APIResponse apiResponse;
        String email = null;

        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") != null ) {
            User user = (User) session.getAttribute("user");
            String[] username = user.getEmail().split("@");
            out.println("<h1>Welcome "+username[0] +"</h1>");
        } else {
            apiResponse = new APIResponse(Messages.Error.USERNOTFOUND);
            response.setStatus(400);
            out.write(mapper.writeValueAsString(apiResponse));
            response.sendRedirect("Login.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
