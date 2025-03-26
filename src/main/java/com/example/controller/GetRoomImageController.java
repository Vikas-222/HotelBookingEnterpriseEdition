package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.dao.IRoomImagesDAO;
import com.example.dao.RoomImagesDAOImpl;
import com.example.dto.RoomImagesDTO;
import com.example.model.RoomImages;
import com.example.service.RoomImagesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="GetRoomImageController", value = "/getroomimages")
public class GetRoomImageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        IRoomImagesDAO iRoomImagesDAO = new RoomImagesDAOImpl();
        RoomImagesService roomImagesService = new RoomImagesService(iRoomImagesDAO);
        try {
        RoomImagesDTO roomImagesDTO = CustomObjectMapper.toObject(request.getReader(),RoomImagesDTO.class);
        List<RoomImages> roomImages = roomImagesService.getRoomImagesByRoomId(roomImagesDTO.getRoomId());
        sendResponse(response,null,null,roomImages,200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(),null, null, 500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
