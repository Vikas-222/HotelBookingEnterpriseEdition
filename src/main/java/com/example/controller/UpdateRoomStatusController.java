package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionValidator;
import com.example.dto.RoomDTO;
import com.example.dto.UserDTO;
import com.example.service.RoomService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateRoomStatusController", value = "/update-room-status")
public class UpdateRoomStatusController extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        RoomService roomService = new RoomService();
        try {
            UserDTO user = SessionValidator.checkSession(request);
            if(!user.getRole().equalsIgnoreCase("admin")){
                throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
            }
            String roomId = request.getParameter("roomId");
            RoomDTO roomDTO = CustomObjectMapper.toObject(request.getReader(), RoomDTO.class);
            RoomDTO room = setRoomId(roomDTO,roomId);
            roomService.updateRoomStatus(room.getRoomId(),room.getRoomStatus().toString());
            sendResponse(response, Messages.ROOM_UPDATED, null, null, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED, e.getMessage(), null, 500);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), null, null, 400);
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

    private RoomDTO setRoomId(RoomDTO roomDTO,String roomStr) throws ApplicationException {
        if (roomStr.isBlank()) {
            throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
        }
        int roomId = Integer.parseInt(roomStr);
        if (roomId <= 0) {
            throw new ApplicationException(Messages.RoomError.ROOM_ID_NOT_FOUND);
        }
       return new RoomDTO.Builder()
                .setRoomId(roomId)
                .setRoomStatus(roomDTO.getRoomStatus()).build();
    }
}
