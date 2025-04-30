package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.enums.Role;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.ImageHandler;
import com.example.common.utils.SessionChecker;
import com.example.dto.RoomDTO;
import com.example.dto.UsersDTO;
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
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,   // 5 MB
        maxRequestSize = 1024 * 1024 * 25 // 25 MB
)
public class AddRoomController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        RoomService roomService = new RoomService();
        try {
            UsersDTO user = SessionChecker.checkSession(request);
            if (user.getRole() != Role.ADMIN) {
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }

            Collection<Part> parts = request.getParts();
            Part roomDetailsPart = null;
            List<Part> imageParts = new ArrayList<>();
            final int MAX_IMAGES = 6;

            for (Part part : parts) {
                String partName = part.getName();
                if ("roomDetails".equals(partName)) {
                    roomDetailsPart = part;
                } else if (partName != null && partName.startsWith("image")) {
                    imageParts.add(part);
                }
            }
            if (roomDetailsPart == null) {
                sendResponse(response, Messages.RoomError.INVALID_VALUES, null, null, 400);
                return;
            }
            RoomDTO.Builder roomDTOBuilder = CustomObjectMapper.toObject(roomDetailsPart.getInputStream(), RoomDTO.Builder.class);
            if (roomDTOBuilder == null) {
                sendResponse(response, Messages.Error.FAILED, null, null, 400);
                return;
            }

            if (imageParts.size() > MAX_IMAGES) {
                // The requirement was less than 6 images (meaning 0 to 5 images).
                throw new ApplicationException(Messages.RoomError.NUMBER_OF_IMAGES_EXCEEDED);
            }

            List<String> imagePaths = new ArrayList<>();
            for (Part imagePart : imageParts) {
                if (imagePart.getSize() > 0) {
                    String imagePath = ImageHandler.getFilePath("RoomImages", imagePart);
                    if (imagePath != null) {
                        imagePaths.add(imagePath);
                        System.out.println("image list " + imagePaths);
                    } else {
                        throw new ApplicationException(Messages.Error.FAILED);
                    }
                } else {
                    throw new ApplicationException(Messages.RoomError.IMAGE_NOT_FOUND);
                }
            }
            roomDTOBuilder.setImagePath(imagePaths);
            RoomDTO roomDTO = roomDTOBuilder.build();
            RoomDTO newRoomEntity = roomService.addRoom(roomDTO);
            sendResponse(response, null, null, newRoomEntity, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, null, 400);
        } catch (ServletException e) {
            e.printStackTrace();
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("size exceeds")) {
                sendResponse(response, Messages.RoomError.IMAGES_SIZE_TOO_LARGE, Messages.Error.PAYLOAD_SIZE_EXCEED, null, 413); // 413 Payload Too Large
            } else {
                sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
            }
        } catch (Exception e) {
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
