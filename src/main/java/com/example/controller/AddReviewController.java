package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.dao.*;
import com.example.dto.ReviewDTO;
import com.example.service.BookingService;
import com.example.service.ReviewService;
import com.example.service.RoomService;
import com.example.service.UserService;
import com.example.validation.ReviewValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AddReviewController", value = "/addReview")
public class AddReviewController extends HttpServlet {
    private IUserDAO iUserDAO = new UserDAOImpl();
    private IBookingDAO iBookingDAO = new BookingDAOImpl();
    private IRoomDAO iRoomDAO = new RoomDAOImpl();
    private RoomService roomService = new RoomService(iRoomDAO);

    private BookingService bookingService = new BookingService(iBookingDAO,roomService);
    private UserService userService = new UserService(iUserDAO);
    private IReviewDAO reviewDAO = new ReviewDAOImpl();
    private ReviewService reviewService;
    @Override
    public void init() {
        reviewService = new ReviewService(reviewDAO,userService,bookingService);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            ReviewDTO review = CustomObjectMapper.toObject(request.getReader(), ReviewDTO.class);
            ReviewValidator.isValidValues(review);
            reviewService.addReview(review);
            sendResponse(response, null,null,null,200);
        }catch(DBException e){
            e.printStackTrace();
            sendResponse(response, Messages.Error.FAILED,e.getMessage(),null,500);
        }
        catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(),null,null,400);
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
