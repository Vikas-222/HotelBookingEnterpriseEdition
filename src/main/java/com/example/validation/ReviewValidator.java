package com.example.validation;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.ReviewDTO;

public class ReviewValidator {

    public static boolean isValidValues(ReviewDTO reviewDTO) throws ApplicationException {
        if(!isValidFeedback(reviewDTO.getFeedback())){
            throw new ApplicationException(Messages.ReviewError.FEEDBACK_EMPTY);
        }
        if(!isValidRating(reviewDTO.getRating())){
            throw new ApplicationException(Messages.ReviewError.RATING_EMPTY);
        }
        return true;
    }

    public static boolean isValidFeedback(String feedback) throws ApplicationException {
        if(feedback.isBlank() || feedback == null){
            return false;
        }
        return true;
    }

    public static boolean isValidRating(int rating) throws ApplicationException {
        if(rating <= 0 || String.valueOf(rating).isBlank()){
            return false;
        }
        return true;
    }
}
