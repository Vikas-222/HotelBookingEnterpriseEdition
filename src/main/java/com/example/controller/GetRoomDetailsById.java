package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.dto.RoomDTO;
import com.example.service.RoomService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetRoomDetailsById", value = "/get-room-details")
public class GetRoomDetailsById extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        RoomService service = new RoomService();
        try {
            String roomId = request.getParameter("roomId");
            int roomID = setRoomId(roomId);
            RoomDTO room = service.getRoomDetails(roomID);
            sendResponse(response, null, null, room, 200);
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

    public int setRoomId(String roomId) throws ApplicationException {
        if (roomId.isBlank()) {
            throw new ApplicationException(Messages.RoomError.ROOM_ID_NOT_FOUND);
        }
        int roomID = Integer.parseInt(roomId);
        if (roomID <= 0) {
            throw new ApplicationException(Messages.RoomError.ROOM_ID_NOT_FOUND);
        }
        return roomID;
    }

}
