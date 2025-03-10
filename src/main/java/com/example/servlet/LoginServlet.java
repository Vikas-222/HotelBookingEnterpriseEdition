package com.example.servlet;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.dao.Userdao;
import com.example.db.DbConnect;
import com.example.entity.User;
import com.example.exception.InvalidCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        Userdao userdao = new Userdao();
        APIResponse apiResponse;

        try {
            User user = mapper.readValue(request.getReader(), User.class);

            boolean flag = userdao.userLogin(user.getEmail(),user.getPasswords());
            if (flag == true) {
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60);
                session.setAttribute("user",user);
                apiResponse = new APIResponse(Messages.LOGINSUCCESSFUL);
                response.setStatus(200);
                out.write(mapper.writeValueAsString(apiResponse));
                response.sendRedirect("home.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            apiResponse = new APIResponse(Messages.Error.FAILED);
            response.setStatus(500);
            out.write(mapper.writeValueAsString(apiResponse));
        } catch (InvalidCredentials e) {
            apiResponse = new APIResponse(Messages.Error.INVALIDCREDENTIALS);
            response.setStatus(400);
            out.write(mapper.writeValueAsString(apiResponse));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            apiResponse = new APIResponse(Messages.Error.FAILED);
            response.setStatus(500);
            out.write(mapper.writeValueAsString(apiResponse));
        }
    }
}
