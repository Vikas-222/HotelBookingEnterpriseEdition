package com.example.dao;

import com.example.common.exception.DBException;
import com.example.model.Review;

import java.util.List;

public interface IReviewDAO {

    boolean addReview(Review review) throws DBException;

    boolean updateReview(Review review) throws DBException;

    boolean deleteReview(int reviewID, int userId) throws DBException;

    List<Review> getReviews() throws DBException;

    List<Review> getReviewsByUserId(int userId) throws DBException;

    boolean isValidReviewId(int id) throws DBException;
}
