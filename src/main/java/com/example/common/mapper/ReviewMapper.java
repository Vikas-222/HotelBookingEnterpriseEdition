package com.example.common.mapper;

import com.example.dto.ReviewDTO;
import com.example.model.Review;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    public static Review convertReviewDTOToEntity(ReviewDTO reviewDTO)
    {
        return new Review(reviewDTO.getReviewId(), reviewDTO.getUserId(), reviewDTO.getBookingId(),
                reviewDTO.getFeedback(), reviewDTO.getRating());
    }

    public static Review convertReviewDTOToEntityForUpdate(ReviewDTO reviewDTO)
    {
        Review review = new Review();
        review.setUserId(reviewDTO.getUserId());
        review.setReviewId(reviewDTO.getReviewId());
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
}
