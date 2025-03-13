package com.example.Controller;

import com.example.APIResponse;
import com.example.common.Messages;
import com.example.dao.IUserDAO;
import com.example.dao.UserDAOImpl;
import com.example.exception.ApplicationException;
import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetOneUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        IUserDAO userdao = new UserDAOImpl();
        APIResponse apiResponse;

        try {
            User user = mapper.readValue(request.getReader(), User.class);
            HttpSession session = request.getSession(false);
            if(session.getAttribute("user") != null){
                if(user.getUserId() != 0) {
                    List<User> list = userdao.getOneUserDetails(user.getUserId());
                    apiResponse = new APIResponse(Messages.FETCHED_USER,list);
                    response.setStatus(200);
                    out.write(mapper.writeValueAsString(apiResponse));
                }
            }
            else{
                throw new ApplicationException(Messages.Error.INVALID_ACTION,ApplicationException.ErrorType.USER_ERROR);
            }
        } catch(ApplicationException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            apiResponse = new APIResponse(e.getMessage());
            response.setStatus(500);
            out.write(mapper.writeValueAsString(apiResponse));
        }
    }
}
