package com.example.common.entitymapper;

import com.example.dto.BookingDTO;
import com.example.dto.ReviewDTO;
import com.example.dto.RoomDTO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.Review;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    public static Review convertReviewDTOToEntity(ReviewDTO reviewDTO, UsersDTO userDTO, BookingDTO booking, RoomDTO roomDTO) {
        Review review = new Review();
        review.setUser(UserMap.dtoToUser(userDTO));
        review.setBooking(BookingMapper.convertBookingDTOToEntity(booking,userDTO,roomDTO));
        review.setFeedback(reviewDTO.getFeedback());
        review.setRating(reviewDTO.getRating());
        return review;
    }

    public static Review convertReviewDTOToEntityForUpdate(ReviewDTO reviewDTO, UsersDTO user, BookingDTO booking,RoomDTO roomDTO) {
        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        review.setUser(UserMap.dtoToUser(user));
        review.setBooking(BookingMapper.convertBookingDTOToEntity(booking,user,roomDTO));
        review.setFeedback(reviewDTO.getFeedback());
        review.setRating(reviewDTO.getRating());
        return review;
    }

    public static List<ReviewDTO> convertEntityListToReviewDTOList(List<Review> list){
        return list.stream().map(item -> new ReviewDTO.Builder()
                .setReviewId(item.getReviewId())
                .setUserId(item.getUser() != null ? item.getUser().getUserId() : 0)
                .setBookingId(item.getBooking() != null ? item.getBooking().getBookingId() : 0)
                .setFeedback(item.getFeedback())
                .setRating(item.getRating())
                .build()).collect(Collectors.toList());
    }


    public static ReviewDTO convertEntityToReviewDTO(Review review){
        return new ReviewDTO.Builder()
                .setReviewId(review.getReviewId())
                .setUserId(review.getUser() != null ? review.getUser().getUserId() : 0)
                .setBookingId(review.getBooking() != null ? review.getBooking().getBookingId() : 0)
                .setFeedback(review.getFeedback())
                .setRating(review.getRating())
                .build();
    }

}
