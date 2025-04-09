package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.ImageHandler;
import com.example.common.utils.SessionValidator;
import com.example.dto.RoomDTO;
import com.example.dto.UserDTO;
import com.example.service.RoomService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "add-room", value = "/add-room")
@MultipartConfig
public class AddRoomController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        RoomService roomService = new RoomService();
        try {
            UserDTO user = SessionValidator.checkSession(request);
            if(!user.getRole().equalsIgnoreCase("admin")){
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            Part roomDetails = request.getPart("roomDetails");
            Collection<Part> parts = request.getParts();

            RoomDTO.Builder roomDTO = CustomObjectMapper.toObject(roomDetails.getInputStream(), RoomDTO.Builder.class);

            List<String> imagePaths = new ArrayList<>();
            for (Part part : parts) {
                if ("images".equals(part.getName()) && part.getSize() > 0) {
                    String imagePath = ImageHandler.getFilePath("RoomImages", part);
                    imagePaths.add(imagePath);
                }
            }
            roomDTO.setImagePath(imagePaths);
            RoomDTO room = roomDTO.build();
            roomService.addRoom(room);
            sendResponse(response, null, null, null, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(),null, null, 400);
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
