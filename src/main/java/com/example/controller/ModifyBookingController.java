package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionValidator;
import com.example.dto.BookingDTO;
import com.example.dto.UserDTO;
import com.example.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "modify-booking", value = "/modify-booking")
public class ModifyBookingController extends HttpServlet {


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        BookingService service = new BookingService();
        try {
            UserDTO user = SessionValidator.checkSession(request);
            BookingDTO booking = CustomObjectMapper.toObject(request.getReader(), BookingDTO.class);
            if(user.getIsActive() == false){
                throw new ApplicationException(Messages.BookingError.ACCOUNT_DEACTIVATE);
            }
            service.modifyBooking(booking);
            sendResponse(response, Messages.BOOKING_UPDATED, null, null, 200);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, null, e.getMessage(), null, 500);
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
