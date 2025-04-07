package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.ImageHandler;
import com.example.dao.IRoomDAO;
import com.example.dao.IRoomImagesDAO;
import com.example.dao.impl.RoomDAOImpl;
import com.example.dao.impl.RoomImagesDAOImpl;
import com.example.dto.RoomDTO;
import com.example.dto.RoomImagesDTO;
import com.example.service.RoomImagesService;
import com.example.service.RoomService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddRoomImageController", value = "/addroomimage")
@MultipartConfig(maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100, fileSizeThreshold = 1024 * 1024 * 5)
public class AddRoomImageController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        RoomService roomService = new RoomService();

//        try{
//        Part roomDetails = request.getPart("roomDetails");
//        RoomDTO roomDTO = CustomObjectMapper.toObject(roomDetails.getInputStream(), RoomDTO.class);
//        List<String> imagePaths = new ArrayList<>();
//        for (Part part : request.getParts()) {
//            if (part.getName().equals("images") && part.getSize() > 0) {
//                String imagePath = ImageHandler.getFilePath("RoomImages", part);
//                imagePaths.add(imagePath);
//            }
//        }
//
//        RoomDTO room = setImagePathsToDTO(roomDTO,imagePaths);
//        roomService.addRoom(room);
//        sendResponse(response, null, null, null, 200);
        try {
            Part roomDetails = request.getPart("roomDetails");
            Part imagePart = request.getPart("image");
            RoomDTO roomDTO = CustomObjectMapper.toObject(roomDetails.getInputStream(), RoomDTO.class);
            RoomDTO room = setImagePathsToDTO(roomDTO, ImageHandler.getFilePath("RoomImages", imagePart));
            roomService.addRoom(room);
            sendResponse(response, null, null, null, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        }
    }

    private void sendResponse(HttpServletResponse response, String message, String technicalMessage, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        Response apiResponse = new Response();
        apiResponse.setMessage(message);
        apiResponse.setTechnicalMessage(technicalMessage);
        apiResponse.setData(data);
        response.getWriter().write(CustomObjectMapper.toString(apiResponse));
    }


//    private RoomDTO setImagePathsToDTO(RoomDTO roomDTO, List<String> list) {
//        return new RoomDTO.Builder().setRoomId(roomDTO.getRoomId())
//                .setRoomNumber(roomDTO.getRoomNumber())
//                .setRoomType(roomDTO.getRoomType())
//                .setPricePerNight(roomDTO.getPricePerNight())
//                .setCapacity(roomDTO.getCapacity())
//                .setImagePath(list).build();
//    }

    private RoomDTO setImagePathsToDTO(RoomDTO roomDTO, String str) {
        return new RoomDTO.Builder().setRoomId(roomDTO.getRoomId())
                .setRoomNumber(roomDTO.getRoomNumber())
                .setRoomType(roomDTO.getRoomType())
                .setPricePerNight(roomDTO.getPricePerNight())
                .setCapacity(roomDTO.getCapacity())
                .setImagePaths(str).build();
    }
}
