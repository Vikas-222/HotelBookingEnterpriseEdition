package com.example.userServlet;


import com.example.APIResponse;
import com.example.common.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        APIResponse apiResponse;
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpSession session = request.getSession(false);

            if(session.getAttribute("user") != null) {
                session.invalidate();
                apiResponse = new APIResponse(Messages.LOGOUT_SUCCESSFUL);
                response.setStatus(200);
            } else {
                apiResponse = new APIResponse(Messages.Error.USER_NOT_FOUND);
                response.setStatus(400);
            }
            out.write(mapper.writeValueAsString(apiResponse));
            response.sendRedirect("Login.html");
        } catch (Exception ex) {
            ex.printStackTrace();
            apiResponse = new APIResponse(Messages.Error.FAILED);
            response.setStatus(500);
            out.write(mapper.writeValueAsString(apiResponse));
            response.sendRedirect("Login.html");
        }
    }
}
