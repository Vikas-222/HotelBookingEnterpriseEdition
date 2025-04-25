package com.example.model;

import java.time.LocalDateTime;

public class Review {

    private int reviewId;
    private int userId;
    private int bookingId;
    private String feedback;
    private byte rating;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Review(int reviewId, int userId, int bookingId, String feedback, byte rating) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.bookingId = bookingId;
        this.feedback = feedback;
        this.rating = rating;
    }

    public Review() {
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return  "reviewId=" + reviewId +
                ", userId=" + userId +
                ", bookingId=" + bookingId +
                ", feedback='" + feedback + '\'' +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt;
    }
}
