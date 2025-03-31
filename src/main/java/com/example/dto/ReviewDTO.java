package com.example.dto;

public class ReviewDTO {

    private int reviewId;
    private int bookingId;
    private int userId;
    private String feedback;
    private int rating;

    public ReviewDTO() {}

    public ReviewDTO(int reviewId, int bookingId, int userId, String feedback, int rating) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.feedback = feedback;
        this.rating = rating;
    }

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}
