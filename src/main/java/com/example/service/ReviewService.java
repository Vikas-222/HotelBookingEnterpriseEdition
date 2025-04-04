package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.ReviewMapper;
import com.example.dao.IReviewDAO;
import com.example.dao.impl.ReviewDAOImpl;
import com.example.dto.ReviewDTO;
import com.example.model.Review;
import java.util.List;

public class ReviewService {
    private IReviewDAO reviewDAO = new ReviewDAOImpl();
    private UserService userService = new UserService();
    private BookingService bookingService = new BookingService();

    public boolean addReview(ReviewDTO reviewDTO) throws ApplicationException {
        Review review = ReviewMapper.convertReviewDTOToEntity(reviewDTO);
        if(bookingService.isValidUserIdAndBookingId(review.getUserId(),review.getBookingId()) == false){
            throw new ApplicationException(Messages.ReviewError.INVALID_USERID_BOOKINGID);
        }
        return reviewDAO.addReview(review);
    }

    public boolean updateReview(ReviewDTO reviewDTO) throws ApplicationException {
        Review review = ReviewMapper.convertReviewDTOToEntityForUpdate(reviewDTO);
        if (userService.isValidUserId(review.getUserId()) == false) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        if (isValidReviewId(reviewDTO.getReviewId()) == false) {
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
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
