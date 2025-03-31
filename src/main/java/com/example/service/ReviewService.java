package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.ReviewMapper;
import com.example.dao.IReviewDAO;
import com.example.dto.ReviewDTO;
import com.example.model.Review;

public class ReviewService {
    private IReviewDAO reviewDAO;
    private UserService userService;
    private BookingService bookingService;

    public ReviewService(IReviewDAO reviewDAO, UserService userService, BookingService bookingService) {
        this.reviewDAO = reviewDAO;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public boolean addReview(ReviewDTO reviewDTO) throws ApplicationException {
        Review review = ReviewMapper.convertReviewDTOToEntity(reviewDTO);
        if(!userService.isValidUserId(review.getUserId())){
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        if(!bookingService.isValidBookingId(review.getBookingId())){
            throw new ApplicationException(Messages.BookingError.BOOKING_NOT_FOUND);
        }
        return reviewDAO.addReview(review);
    }

//    public boolean updateReview(ReviewDTO review) {
//        return reviewDAO.updateReview(review);
//    }
//
//    public boolean deleteReview(int reviewID) {
//        return reviewDAO.deleteReview(reviewID);
//    }
//
//    public List<ReviewDTO> getReviewsByBooking(int bookingID) {
//        return reviewDAO.getReviewsByBooking(bookingID);
//    }
}
