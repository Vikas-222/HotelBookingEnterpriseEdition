package com.example.servlet;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.common.Validation;
import com.example.dao.Userdao;
import com.example.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        APIResponse apiResponse;
        try {

            User user = mapper.readValue(request.getReader(), User.class);

            if (user.getFirstName() == null || user.getEmail() == null || user.getPasswords() == null || user.getContact() == null || user.getFirstName().trim().isEmpty() || user.getEmail().trim().isEmpty() || user.getPasswords().trim().isEmpty() || user.getContact().trim().isEmpty()) {
                apiResponse = new APIResponse(Messages.Error.INVALIDVALUES);
                response.setStatus(400);
                out.write(mapper.writeValueAsString(apiResponse));
                return;
            }

            if (!Validation.isValidPassword(user.getPasswords())) {
                apiResponse = new APIResponse(Messages.Error.WEAKPASSWORD);
                response.setStatus(400);
                out.write(mapper.writeValueAsString(apiResponse));
                return;
            }

            if (!Validation.isValidEmail(user.getEmail())) {
                apiResponse = new APIResponse(Messages.Error.INVALIDEMAIL);
                response.setStatus(400);
                out.write(mapper.writeValueAsString(apiResponse));
                return;
            }

            if (!Validation.isValidContact(user.getContact())) {
                apiResponse = new APIResponse(Messages.Error.INVALIDCONTACT);
                response.setStatus(400);
                out.write(mapper.writeValueAsString(apiResponse));
                return;
            }

            Userdao userdao = new Userdao();
            userdao.addUser(user);
            apiResponse = new APIResponse(Messages.ACCOUNTCREATED);
            response.setStatus(200);
            out.write(mapper.writeValueAsString(apiResponse));
            response.sendRedirect("home.html");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            apiResponse = new APIResponse(Messages.Error.FAILED);
            response.setStatus(500);
            out.write(mapper.writeValueAsString(apiResponse));
        }
    }
}
