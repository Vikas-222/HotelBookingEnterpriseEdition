package com.example.controller;

import com.example.common.AppConstants;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionChecker;
import com.example.dto.BookingDTO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.Booking;
import com.example.entityservice.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AddBookingController", value = "/addbooking")
public class AddBookingController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstants.APPLICATION_JSON);
        BookingService bookingService = new BookingService();
        try {
            UsersDTO user = SessionChecker.checkSession(request);
            BookingDTO bookingDTO = CustomObjectMapper.toObject(request.getReader(), BookingDTO.class);
            if(!user.getIsActive()){
                throw new ApplicationException(Messages.BookingError.ACCOUNT_DEACTIVATE);
            }
            Booking booking = bookingService.addBooking(bookingDTO,user);
            sendResponse(response, Messages.BOOKING_SUCCESS, null, booking, 200);
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
