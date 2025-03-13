package com.example.userController;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.common.Response;
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
            apiResponse = new APIResponse(Messages.Error.USER_NOT_FOUND);
            Response.responseMethod(response, 400, apiResponse);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
