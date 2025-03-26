package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionValidator;
import com.example.dao.IRoomDAO;
import com.example.dao.IRoomImagesDAO;
import com.example.dao.RoomDAOImpl;
import com.example.dao.RoomImagesDAOImpl;
import com.example.dto.RoomImagesDTO;
import com.example.dto.UserDTO;
import com.example.service.RoomImagesService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet(name = "AddRoomImageController", value = "/addroomimage")
@MultipartConfig(maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100, fileSizeThreshold = 1024 * 1024 * 5)
public class AddRoomImageController extends HttpServlet {

    private static final String IMAGE_UPLOAD_DIRECTORY = "D:\\Demo-Git\\HotelBookingEnterpriseEdition\\src\\main\\webapp\\Images\\RoomImages";
    IRoomImagesDAO roomImage = new RoomImagesDAOImpl();
    IRoomDAO iRoomDAO = new RoomDAOImpl();
    RoomImagesService imagesService = new RoomImagesService(roomImage, iRoomDAO);
    RoomImagesDTO roomImagesDTO = new RoomImagesDTO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            UserDTO userDTO = SessionValidator.checkSession(request);
            if (!userDTO.getRole().equalsIgnoreCase("Admin")) {
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            imageHandler(request);
            sendResponse(response, Messages.IMAGE_UPLOADED, null, null, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, null, 500);
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

    private void imageHandler(HttpServletRequest request) throws ApplicationException, IOException, ServletException {
        try {
            Part filePart = request.getPart("image");
            if (filePart == null || filePart.getSize() == 0 || filePart.getSubmittedFileName().isEmpty()) {
                throw new ApplicationException(Messages.RoomError.INVALID_IMAGE);
            }
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String filePath = IMAGE_UPLOAD_DIRECTORY + File.separator + fileName;
            InputStream fileContent = filePart.getInputStream();
            Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            roomImagesDTO.setImagePath(filePath);
            String roomIdStr = request.getParameter("roomId");
            if (roomIdStr == null || roomIdStr.isBlank()) {
                throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
            }
            int roomId = Integer.parseInt(roomIdStr);
            if (roomId <= 0) {
                throw new ApplicationException(Messages.RoomError.INVALID_ROOM_ID);
            }
            roomImagesDTO.setRoomId(roomId);

            imagesService.saveRoomImage(roomImagesDTO);
            CustomObjectMapper.toString(roomImagesDTO);
        } catch (DBException e) {
             throw e;
        } catch (ApplicationException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (ServletException e) {
            throw e;
        }
    }

}
