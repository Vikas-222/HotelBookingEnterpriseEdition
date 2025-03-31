package com.example.dao;

import com.example.common.exception.DBException;
import com.example.model.Review;

import java.util.List;

public interface IReviewDAO {

    boolean addReview(Review review) throws DBException;

    boolean updateReview(Review review) throws DBException;

    boolean deleteReview(int reviewID) throws DBException;

    List<Review> getReviewsByBooking(int bookingID) throws DBException;
}
