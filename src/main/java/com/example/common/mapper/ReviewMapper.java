package com.example.common.mapper;

import com.example.dto.ReviewDTO;
import com.example.model.Review;

public class ReviewMapper {

    public static Review convertReviewDTOToEntity(ReviewDTO reviewDTO)
    {
        return new Review(reviewDTO.getReviewId(), reviewDTO.getUserId(), reviewDTO.getBookingId(),
                reviewDTO.getFeedback(), reviewDTO.getRating());
    }
}
