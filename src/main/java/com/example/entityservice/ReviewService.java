package com.example.entityservice;

import com.example.common.Messages;
import com.example.common.entitymapper.ReviewMapper;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.BookingMapper;
import com.example.controller.validation.ReviewValidator;
import com.example.dao.entity.ReviewDAO;
import com.example.dto.BookingDTO;
import com.example.dto.ReviewDTO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.Review;
import com.example.service.BookingService;

public class ReviewService {
    private ReviewDAO reviewDAO = new ReviewDAO();
    private UserServices userService = new UserServices();
    private BookingService bookingService = new BookingService();

    public boolean addReview(ReviewDTO reviewDTO,UsersDTO usersDTO) throws ApplicationException {
        if(!bookingService.isValidUserIdAndBookingId(usersDTO.getUserId(), reviewDTO.getBookingId())){
            throw new ApplicationException(Messages.ReviewError.INVALID_USERID_BOOKINGID);
        }
        ReviewValidator.isValidValues(reviewDTO);
        UsersDTO user = userService.fetchUserDetailsById(usersDTO.getUserId());
        BookingDTO booking = bookingService.getBookingDetails(reviewDTO.getBookingId());
        Review review = ReviewMapper.convertReviewDTOToEntity(reviewDTO,user,booking);
        return reviewDAO.addReview(review);
    }

    public void updateReview(ReviewDTO reviewDTO, UsersDTO user) throws ApplicationException {
        ReviewValidator.isValidValuesForUpdate(reviewDTO);
        if (!userService.isValidUserId(user.getUserId())) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        if (!isValidReviewId(reviewDTO.getReviewId())) {
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
        Review existingReview = reviewDAO.findReview(reviewDTO.getReviewId());
        if(existingReview == null){
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
        BookingDTO bookingDTO = BookingMapper.convertEntityToBookingDTO(existingReview.getBooking());
        System.out.println("bookingdto in reviewservice "+bookingDTO);
        Review review = ReviewMapper.convertReviewDTOToEntityForUpdate(reviewDTO,user,bookingDTO);
        System.out.println("review "+review);
        reviewDAO.updateReview(review);
    }

    public boolean deleteReview(String reviewID, int userId) throws ApplicationException {
        if (reviewID == null || reviewID.isBlank()) {
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
        int reviewId;
        try {
            reviewId = Integer.parseInt(reviewID);
        } catch (NumberFormatException e) {
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
        if (reviewId <= 0) {
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
        if (userService.isValidUserId(userId) == false) {
            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
        }
        if (reviewDAO.deleteReview(reviewId, userId) == false) {
            throw new ApplicationException(Messages.ReviewError.INVALID_REVIEW_ID);
        }
        return true;
    }

//    public List<ReviewDTO> getReviewsByUserId(int userId) throws ApplicationException {
//        if (userService.isValidUserId(userId) == false) {
//            throw new ApplicationException(Messages.Error.USER_NOT_FOUND);
//        }
//        return ReviewMapper.convertEntityListToReviewDTOList(reviewDAO.getReviewsByUserId(userId));
//    }
//
//    public List<ReviewDTO> getReviews() throws ApplicationException {
//        return ReviewMapper.convertEntityListToReviewDTOList(reviewDAO.getReviews());
//    }
//
    public boolean isValidReviewId(int id) throws DBException {
        return reviewDAO.isValidReviewId(id);
    }

}
