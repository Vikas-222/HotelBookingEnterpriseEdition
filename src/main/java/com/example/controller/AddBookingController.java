package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionValidator;
import com.example.dao.BookingDAOImpl;
import com.example.dao.IBookingDAO;
import com.example.dao.IRoomDAO;
import com.example.dao.RoomDAOImpl;
import com.example.dto.BookingDTO;
import com.example.dto.UserDTO;
import com.example.service.BookingService;
import com.example.service.RoomService;
import com.example.validation.BookingValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AddBookingController", value = "/addbooking")
public class AddBookingController extends HttpServlet {
    IBookingDAO iBookingDAO = new BookingDAOImpl();
    IRoomDAO iRoomDAO = new RoomDAOImpl();
    RoomService roomService = new RoomService(iRoomDAO);
    BookingService bookingService = new BookingService(iBookingDAO, roomService);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            UserDTO userDTO = SessionValidator.checkSession(request);
            BookingDTO bookingDTO = CustomObjectMapper.toObject(request.getReader(), BookingDTO.class);
            BookingValidator.validateBooking(bookingDTO);
            BookingDTO bookingDTO1 = bookingService.setUserIdAndTotalAmount(bookingDTO, userDTO.getUserId(), bookingDTO.getRoomNumber());
            bookingService.addBooking(bookingDTO1);
            sendResponse(response, Messages.BOOKING_SUCCESS, null, null, 200);
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
}
