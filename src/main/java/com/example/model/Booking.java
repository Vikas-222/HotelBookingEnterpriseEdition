package com.example.model;

import com.example.common.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booking {

    private int bookingId;
    private int userId;
    private int roomNumber;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private float totalAmount;
    private BookingStatus bookingStatus;
    private Date cancellationDate;
    private float refundAmount;
    private int numberOfGuests;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Booking(){}

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.userId = builder.userId;
        this.roomNumber = builder.roomNumber;
        this.checkInTime = builder.checkInTime;
        this.checkOutTime = builder.checkOutTime;
        this.totalAmount = builder.totalAmount;
        this.bookingStatus = builder.bookingStatus;
        this.cancellationDate = builder.cancellationDate;
        this.refundAmount = builder.refundAmount;
        this.numberOfGuests = builder.numberOfGuests;
        this.created_at = builder.created_at;
        this.updated_at = builder.updated_at;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public static class Builder {
        private int bookingId;
        private int userId;
        private int roomNumber;
        private LocalDateTime checkInTime;
        private LocalDateTime checkOutTime;
        private float totalAmount;
        private BookingStatus bookingStatus;
        private Date cancellationDate;
        private float refundAmount;
        private int numberOfGuests;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;

        public Builder setBookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder setCheckInTime(LocalDateTime checkInTime) {
            this.checkInTime = checkInTime;
            return this;
        }

        public Builder setCheckOutTime(LocalDateTime checkOutTime) {
            this.checkOutTime = checkOutTime;
            return this;
        }

        public Builder setTotalAmount(float totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setBookingStatus(BookingStatus bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public Builder setCancellationDate(Date cancellationDate) {
            this.cancellationDate = cancellationDate;
            return this;
        }

        public Builder setRefundAmount(float refundAmount) {
            this.refundAmount = refundAmount;
            return this;
        }

        public Builder setNumberOfGuests(int numberOfGuests) {
            this.numberOfGuests = numberOfGuests;
            return this;
        }

        public Builder setCreated_at(LocalDateTime created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder setUpdated_at(LocalDateTime updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }

    @Override
    public String toString() {
        return  "bookingId=" + bookingId +
                ", userId=" + userId +
                ", roomNumber=" + roomNumber +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", totalAmount=" + totalAmount +
                ", bookingStatus=" + bookingStatus +
                ", cancellationDate=" + cancellationDate +
                ", refundAmount=" + refundAmount +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at;
    }
}
