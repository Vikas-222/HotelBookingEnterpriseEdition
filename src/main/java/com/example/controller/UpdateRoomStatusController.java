package com.example.controller;


import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.dao.IRoomDAO;
import com.example.dao.RoomDAOImpl;
import com.example.dto.RoomDTO;
import com.example.service.RoomService;
import com.example.validation.RoomValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UpdateRoomStatusController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        IRoomDAO iRoomDAO = new RoomDAOImpl();
        RoomValidator roomValidator = new RoomValidator();
        RoomService roomService = new RoomService(iRoomDAO);
        try {
            RoomDTO roomDTO = CustomObjectMapper.toObject(request.getReader(), RoomDTO.class);
            roomValidator.ValidateForUpdate(roomDTO);
            roomService.updateRoomStatus(roomDTO);
            sendResponse(response, Messages.ROOM_UPDATED, null, null, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, null, 400);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response,Messages.Error.FAILED, e.getMessage(),null,500);
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
