package com.example.dto;

import com.example.common.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private int bookingId;
    private int userId;
    private int roomId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private float totalAmount;
    private BookingStatus bookingStatus;
    private Date cancellationDate;
    private float refundAmount;
    private float gstRates;
    private int numberOfGuests;

    public BookingDTO(){}

    private BookingDTO(Builder builder) {
        this.bookingId = builder.bookingId;
        this.userId = builder.userId;
        this.roomId = builder.roomId;
        this.checkInTime = builder.checkInTime;
        this.checkOutTime = builder.checkOutTime;
        this.totalAmount = builder.totalAmount;
        this.bookingStatus = builder.bookingStatus;
        this.cancellationDate = builder.cancellationDate;
        this.refundAmount = builder.refundAmount;
        this.gstRates = builder.gstRates;
        this.numberOfGuests = builder.numberOfGuests;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomId() {
        return roomId;
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

    public float getGstRates() {
        return gstRates;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }


    public static class Builder {
        public String serviceName;
        private int bookingId;
        private int userId;
        private int roomId;
        private LocalDateTime checkInTime;
        private LocalDateTime checkOutTime;
        private float totalAmount;
        private BookingStatus bookingStatus;
        private Date cancellationDate;
        private float refundAmount;
        private float gstRates;
        private int numberOfGuests;

        public Builder setBookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setRoomId(int roomId) {
            this.roomId = roomId;
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

        public Builder setGstRates(float gstRates) {
            this.gstRates = gstRates;
            return this;
        }

        public Builder setNumberOfGuests(int numberOfGuests) {
            this.numberOfGuests = numberOfGuests;
            return this;
        }

        public BookingDTO build() {
            return new BookingDTO(this);
        }
    }

    @Override
    public String toString() {
        return "BookingId=" + bookingId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", totalAmount=" + totalAmount +
                ", bookingStatus=" + bookingStatus +
                ", cancellationDate=" + cancellationDate +
                ", refundAmount=" + refundAmount +
                ", gstRates=" + gstRates +
                ", numberOfGuests=" + numberOfGuests;
    }
}
