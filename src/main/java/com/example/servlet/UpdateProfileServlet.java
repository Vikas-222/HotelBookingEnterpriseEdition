//package com.example.servlet;
//
//import com.example.dao.Userdao;
//import com.example.db.DbConnect;
//import com.example.entity.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//
//public class UpdateProfileServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//
////        ------------------------------------------------------------------------
////        fetch not working in get method
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        ObjectMapper mapper = new ObjectMapper();
//        Userdao userdao = new Userdao();
//
//        try {
//            User user = mapper.readValue(request.getReader(),User.class);
//            Connection connection = DbConnect.getConnection();
//
//            List<User> list = userdao.fetchUser(user);
//
//            if(!list.isEmpty()){
//                response.setStatus(HttpServletResponse.SC_OK);
//                out.write("{\"success\":\""+mapper.writeValueAsString(list)+"\"}");
//            }
//            else{
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                out.write("{\"error\":\"User not found\"}");
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
