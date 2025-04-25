package com.example.dto;

import com.example.common.exception.ApplicationException;

public class ReviewDTO {

    private int reviewId;
    private int userId;
    private int bookingId;
    private String feedback;
    private byte rating;

    private ReviewDTO(Builder builder) {
        this.reviewId = builder.reviewId;
        this.userId = builder.userId;
        this.bookingId = builder.bookingId;
        this.feedback = builder.feedback;
        this.rating = builder.rating;
    }
    public ReviewDTO() {
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getFeedback() {
        return feedback;
    }

    public byte getRating() {
        return rating;
    }

    public static class Builder {
        private int reviewId;
        private int userId;
        private int bookingId;
        private String feedback;
        private byte rating;

        public Builder setReviewId(int reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setBookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setFeedback(String feedback) {
            this.feedback = feedback;
            return this;
        }

        public Builder setRating(byte rating){
            this.rating = rating;
            return this;
        }


        public ReviewDTO build() {
            return new ReviewDTO(this);
        }
    }

    @Override
    public String toString() {
        return "reviewId=" + reviewId +
                ", userId=" + userId +
                ", bookingId=" + bookingId +
                ", feedback='" + feedback + '\'' +
                ", rating=" + rating;
    }
}
