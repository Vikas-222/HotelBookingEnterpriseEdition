package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.ImageHandler;
import com.example.dao.IRoomDAO;
import com.example.dao.RoomDAOImpl;
import com.example.dto.RoomDTO;
import com.example.service.RoomService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddRoomController", value = "/addRoom")
public class AddRoomController extends HttpServlet {

    private static final String IMAGE_UPLOAD_DIRECTORY = "D:\\Demo-Git\\HotelBookingEnterpriseEdition\\src\\main\\webapp\\Images\\RoomImages";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        IRoomDAO iRoomDAO = new RoomDAOImpl();
        RoomService roomService = new RoomService(iRoomDAO);
        try {
            RoomDTO room = ImageHandler.parseRoomFromRequest(request);
            List<String> imagePaths = ImageHandler.handleImageUpload(request);
            int roomId = roomService.addRoom(room);
            roomService.saveImagePathsToDatabase(imagePaths, roomId);
            sendResponse(response, Messages.ROOM_ADDED, null, null, 200);
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

}
