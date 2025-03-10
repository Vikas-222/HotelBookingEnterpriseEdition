package com.example.servlet;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.dao.Userdao;
import com.example.db.DbConnect;
import com.example.entity.User;
import com.example.exception.InvalidCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FetchOneUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        Userdao userdao = new Userdao();
        APIResponse apiResponse;

        try {
            User user = mapper.readValue(request.getReader(), User.class);

            if(user.getEmail() != null) {
                List<User> list = userdao.getOneUserDetails(user.getEmail());
                apiResponse = new APIResponse(list);
                response.setStatus(200);
                out.write(mapper.writeValueAsString(apiResponse));
            }
        } catch (InvalidCredentials e) {
            apiResponse = new APIResponse(Messages.Error.USERNOTFOUND);
            response.setStatus(400);
            out.write(mapper.writeValueAsString(apiResponse));
        } catch (SQLException e) {
            e.printStackTrace();
            apiResponse = new APIResponse(Messages.Error.FAILED);
            response.setStatus(500);
            out.write(mapper.writeValueAsString(apiResponse));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            apiResponse = new APIResponse(Messages.Error.FAILED);
            response.setStatus(500);
            out.write(mapper.writeValueAsString(apiResponse));
        }
    }
}
