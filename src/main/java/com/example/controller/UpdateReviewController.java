package com.example.controller;

import com.example.common.AppConstant;
import com.example.common.Messages;
import com.example.common.Response;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.utils.CustomObjectMapper;
import com.example.common.utils.SessionValidator;
import com.example.dto.ReviewDTO;
import com.example.dto.UserDTO;
import com.example.service.ReviewService;
import com.example.controller.validation.ReviewValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateReviewController", value = "/update-review")
public class UpdateReviewController extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        ReviewService reviewService = new ReviewService();
        try {
            UserDTO user = SessionValidator.checkSession(request);
            ReviewDTO reviewDTO = CustomObjectMapper.toObject(request.getReader(), ReviewDTO.class);
            String id = request.getParameter("reviewId");
            if (id == null || id.isBlank()) {
                throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
            }
            int reviewId = Integer.parseInt(id);
            if (reviewId <= 0) {
                throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
            }
            ReviewDTO review = setUserId(reviewDTO, user.getUserId(), reviewId);
            ReviewValidator.isValidValues(review);
            reviewService.updateReview(review);
            sendResponse(response, null, null, null, 200);
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

    private ReviewDTO setUserId(ReviewDTO reviewDTO, int id, int reviewId) {
        return new ReviewDTO.Builder()
                .setUserId(id)
                .setReviewId(reviewId)
                .setBookingId(reviewDTO.getBookingId())
                .setFeedback(reviewDTO.getFeedback())
                .setRating(reviewDTO.getRating())
                .build();
    }
}
