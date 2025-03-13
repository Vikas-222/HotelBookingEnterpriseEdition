//package com.example.roomServlet;
//
//import com.example.APIResponse;
//import com.example.common.Messages;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//
//
//public class addRoomServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        RoomDao roomDao = new RoomDao();
//        APIResponse apiResponse;
//        PrintWriter out = response.getWriter();
//
//        try {
//            Room room = mapper.readValue(request.getReader(), Room.class);
//
//            if (room.getRoomNumber() == 0 || room.getCapacity() == 0 || room.getRoomType() == null || room.getPricePerNight() == 0.0f ||room.getImagePath() == null || room.getImagePath().trim().isEmpty()) {
//                apiResponse = new APIResponse(Messages.Error.INVALIDVALUES);
//                response.setStatus(400);
//                out.write(mapper.writeValueAsString(apiResponse));
//                return;
//            }
//
//            int num = roomDao.addRoom(room);
//            System.out.println(num);
//            if(num != 0){
//                System.out.println("if");
//                apiResponse = new APIResponse(Messages.ROOMADDED);
//                response.setStatus(200);
//                out.write(mapper.writeValueAsString(apiResponse));
//            }
//            else{
//                System.out.println("else");
//                apiResponse = new APIResponse(Messages.Error.ERROROCCURED);
//                response.setStatus(400);
//                out.write(mapper.writeValueAsString(apiResponse));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("sql exception");
//            apiResponse = new APIResponse(e.getMessage());
//            response.setStatus(500);
//            out.write(mapper.writeValueAsString(apiResponse));
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            apiResponse = new APIResponse(e.getMessage());
//            response.setStatus(500);
//            out.write(mapper.writeValueAsString(apiResponse));
//        } catch (AlreadyExistsException e) {
//            e.printStackTrace();
//            System.out.println("already");
//            apiResponse = new APIResponse(e.getMessage());
//            response.setStatus(500);
//            out.write(mapper.writeValueAsString(apiResponse));
//        }
//    }
//}
