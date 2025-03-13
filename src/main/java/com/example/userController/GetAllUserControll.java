//package com.example.userServlet;
//
//import com.example.APIResponse;
//import com.example.common.Messages;
//import com.example.dao.UserDao;
//import com.example.entity.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//public class GetAllUserServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        UserDao userdao = new UserDao();
//        APIResponse apiResponse;
//        PrintWriter out = response.getWriter();
//
//        try {
//            HttpSession session = request.getSession(false);
//            if (session.getAttribute("user") != null) {
////                User user = (User) request.getAttribute("user");
////                if (user.getRoles() == "admin") {
//                    List<User> userList = userdao.getAllUser();
//                    if (userList == null) {
//                        apiResponse = new APIResponse(Messages.Error.NOUSERFOUND);
//                        response.setStatus(400);
//                        out.write(mapper.writeValueAsString(apiResponse));
//                    } else {
//                        apiResponse = new APIResponse(userList);
//                        response.setStatus(200);
//                        out.write(mapper.writeValueAsString(apiResponse));
//                    }
//                } else {
//                    apiResponse = new APIResponse(Messages.Error.INVALIDACTION);
//                    response.setStatus(400);
//                    out.write(mapper.writeValueAsString(apiResponse));
//                }
////            }
//
//    } catch (Exception e) {
//            System.out.println(e.getMessage());
//            apiResponse = new APIResponse(Messages.Error.FAILED);
//            response.setStatus(500);
//            out.write(mapper.writeValueAsString(apiResponse));
//        }
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//
//    }
//}
