package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.ReviewMapper;
import com.example.controller.validation.ReviewValidator;
import com.example.dao.IReviewDAO;
import com.example.dao.impl.ReviewDAOImpl;
import com.example.dto.BookingDTO;
import com.example.dto.ReviewDTO;
import com.example.dto.UsersDTO;
import com.example.entityservice.UserServices;
import com.example.model.Review;

import java.util.List;

public class ReviewService {
    private IReviewDAO reviewDAO = new ReviewDAOImpl();
    private UserServices userService = new UserServices();
    private BookingService bookingService = new BookingService();

    public boolean addReview(ReviewDTO reviewDTO) throws ApplicationException {
        if(bookingService.isValidUserIdAndBookingId(reviewDTO.getUserId(),reviewDTO.getBookingId()) == false){
            throw new ApplicationException(Messages.ReviewError.INVALID_USERID_BOOKINGID);
        }
        ReviewValidator.isValidValues(reviewDTO);
        Review review = ReviewMapper.convertReviewDTOToEntity(reviewDTO);
        return reviewDAO.addReview(review);
    }

    public boolean updateReview(ReviewDTO reviewDTO) throws ApplicationException {
        UsersDTO user = userService.fetchUserDetailsById(reviewDTO.getUserId());
        BookingDTO booking = bookingService.getBookingDetails(reviewDTO.getBookingId());
        if (userService.isValidUserId(reviewDTO.getUserId()) == false) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        if (isValidReviewId(reviewDTO.getReviewId()) == false) {
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
        Review review = ReviewMapper.convertReviewDTOToEntityForUpdate(reviewDTO);
        return reviewDAO.updateReview(review);
    }

    public boolean deleteReview(int reviewID, int userId) throws ApplicationException {
        if (userService.isValidUserId(userId) == false) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        if (reviewDAO.deleteReview(reviewID, userId) == false) {
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
        return true;
    }

    public List<ReviewDTO> getReviewsByUserId(int userId) throws ApplicationException {
        if (userService.isValidUserId(userId) == false) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        return ReviewMapper.convertEntityListToReviewDTOList(reviewDAO.getReviewsByUserId(userId));
    }

    public List<ReviewDTO> getReviews() throws ApplicationException {
        return ReviewMapper.convertEntityListToReviewDTOList(reviewDAO.getReviews());
    }

    public boolean isValidReviewId(int id) throws DBException {
        return reviewDAO.isValidReviewId(id);
    }

}
