package com.example.dao.impl;

import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dao.IReviewDAO;
import com.example.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements IReviewDAO {

    @Override
    public boolean addReview(Review review) throws DBException {
        String sql = "INSERT INTO review (booking_id, user_id, feedback, rating) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, review.getBookingId());
            ps.setInt(2, review.getUserId());
            ps.setString(3, review.getFeedback());
            ps.setByte(4, review.getRating());
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
            ps.setByte(2, review.getRating());
            ps.setInt(3, review.getReviewId());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean deleteReview(int reviewID, int userId) throws DBException {
        String find = "SELECT * FROM review WHERE review_id = ? and user_id = ?";
        String sql = "DELETE FROM review WHERE review_id = ?";
        ResultSet rs;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(find);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            pst.setInt(1, reviewID);
            pst.setInt(2, userId);
            rs = pst.executeQuery();
            if (!rs.next()) {
                return false;
            }
            ps.setInt(1, reviewID);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Review> getReviews() throws DBException {
        List<Review> reviewList = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM review";
        try (Connection connection = DbConnect.instance.getConnection();
             Statement ps = connection.createStatement()) {
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getInt("booking_id"),
                        rs.getString("feedback"),
                        rs.getByte("rating")
                );
                reviewList.add(review);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
        return reviewList;
    }

    @Override
    public List<Review> getReviewsByUserId(int userId) throws DBException {
        List<Review> reviewList = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM review WHERE user_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getInt("booking_id"),
                        rs.getString("feedback"),
                        rs.getByte("rating")
                );
                reviewList.add(review);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
        return reviewList;
    }

    @Override
    public boolean isValidReviewId(int id) throws DBException {
        ResultSet rs = null;
        String sql = "SELECT * FROM review WHERE review_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
    }
}

