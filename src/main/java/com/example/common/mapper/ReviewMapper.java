package com.example.common.mapper;

import com.example.common.entitymapper.UserMap;
import com.example.dto.BookingDTO;
import com.example.dto.ReviewDTO;
import com.example.dto.UsersDTO;
import com.example.model.Review;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    public static Review convertReviewDTOToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setUserId(reviewDTO.getUserId());
        review.setBookingId(review.getBookingId());
        review.setFeedback(reviewDTO.getFeedback());
        review.setRating(reviewDTO.getRating());
        return review;
    }

    public static Review convertReviewDTOToEntityForUpdate(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        review.setUserId(reviewDTO.getUserId());
        review.setBookingId(reviewDTO.getBookingId());
        review.setFeedback(reviewDTO.getFeedback());
        review.setRating(reviewDTO.getRating());
        return review;
    }



    public static List<ReviewDTO> convertEntityListToReviewDTOList(List<Review> list){
        return list.stream().map(item -> new ReviewDTO.Builder()
                .setReviewId(item.getReviewId())
                .setUserId(item.getUserId())
                .setBookingId(item.getBookingId())
                .setFeedback(item.getFeedback())
                .setRating(item.getRating()).build()).collect(Collectors.toList());
    }



//    public static List<ReviewDTO> convertEntityListToReviewDTOList(List<Review> list){
//        return list.stream().map(item -> new ReviewDTO.Builder()
//                .setReviewId(item.getReviewId())
//                .setUserId(item.getUser() != null ? item.getUser().getUserId() : 0)
//                .setBookingId(item.getBooking() != null ? item.getBooking().getBookingId() : 0)
//                .setFeedback(item.getFeedback())
//                .setRating(item.getRating())
//                .build()).collect(Collectors.toList());
//    }
}
