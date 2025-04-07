//package com.example.controller;
//
//import com.example.common.AppConstants;
//import com.example.common.Messages;
//import com.example.common.Response;
//import com.example.common.exception.ApplicationException;
//import com.example.common.exception.DBException;
//import com.example.common.utils.CustomObjectMapper;
//import com.example.common.utils.ImageHandler;
//import com.example.dto.RoomDTO;
//import com.example.service.RoomService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//
//import java.io.IOException;
//
//@WebServlet(name = "add-room", value = "/add-room")
//public class AddRoomController extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType(AppConstants.APPLICATION_JSON);
//        RoomService roomService = new RoomService();
//        try{
//            Part roomDetails = request.getPart("roomDetails");
//            Part imagePart = request.getPart("image");
//            RoomDTO roomDTO = CustomObjectMapper.toObject(roomDetails.getInputStream(),RoomDTO.class);
//
//            roomDTO.setImagePath(ImageHandler.getFilePath(AppConstants.FOOD_ITEM_IMAGE_FOLDER, imagePart));
//            roomService.addRoom(roomDTO);
//            sendResponse(response,null,null,null,200);
//        } catch (DBException e) {
//            e.printStackTrace();
//            sendResponse(response, Messages.Error.FAILED,e.getMessage(),null,500);
//        }catch(ApplicationException e){
//            e.printStackTrace();
//            sendResponse(response,e.getMessage(),null,null,400);
//        }
//    }
//
//
//    private void sendResponse(HttpServletResponse response, String message, String technicalMessage, Object data, int statusCode) throws IOException {
//        response.setStatus(statusCode);
//        Response apiResponse = new Response();
//        apiResponse.setMessage(message);
//        apiResponse.setTechnicalMessage(technicalMessage);
//        apiResponse.setData(data);
//        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
//    }
//}
