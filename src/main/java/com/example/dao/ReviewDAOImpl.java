package com.example.dao;

import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements IReviewDAO{

    @Override
    public boolean addReview(Review review) throws DBException {
        String sql = "INSERT INTO review (booking_id, user_id, feedback, rating) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, review.getBookingId());
            ps.setInt(2, review.getUserId());
            ps.setString(3, review.getFeedback());
            ps.setInt(4, review.getRating());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean updateReview(Review review) throws DBException {
        String sql = "UPDATE review SET feedback = ?, rating = ? WHERE review_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, review.getFeedback());
            ps.setInt(2, review.getRating());
            ps.setInt(3, review.getReviewId());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean deleteReview(int reviewID) throws DBException {
        String sql = "DELETE FROM review WHERE review_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, reviewID);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Review> getReviewsByBooking(int bookingID) throws DBException {
        List<Review> reviews = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM review WHERE booking_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingID);
            rs = ps.executeQuery();
            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getInt("booking_id"),
                        rs.getString("description"),
                        rs.getInt("rating")
                );
                reviews.add(review);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
        return reviews;
    }
}

