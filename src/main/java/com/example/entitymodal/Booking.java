package com.example.entitymodal;

import com.example.common.enums.BookingStatus;
import com.example.model.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOutTime;

    @Column(name = "total_amount", nullable = false)
    private float totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus;

    @Column(name = "cancellation_date", columnDefinition = "DATE", nullable = true)
    private LocalDate cancellationDate;

    @Column(name = "refund_amount")
    private float refundAmount;

    @Column(name = "gstRate", nullable = false)
    private float gstRates;

    @Column(name = "numberOfGuests", nullable = false)
    private int numberOfGuests;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Booking() {
    }

    public Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.user = builder.user;
        this.room = builder.room;
        this.checkInTime = builder.checkIn;
        this.checkOutTime = builder.checkOut;
        this.totalAmount = builder.totalAmount;
        this.bookingStatus = builder.bookingStatus;
        this.cancellationDate = builder.cancellationDate;
        this.refundAmount = builder.refundAmount;
        this.gstRates = builder.gstRates;
        this.numberOfGuests = builder.numberOfGuests;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public int getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
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

    public LocalDate getCancellationDate() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder {
        private int bookingId;
        private User user;
        private Room room;
        private LocalDateTime checkIn;
        private LocalDateTime checkOut;
        private float totalAmount;
        private BookingStatus bookingStatus;
        private LocalDate cancellationDate;
        private float refundAmount;
        private float gstRates;
        private int numberOfGuests;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder setBookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setCheckInTime(LocalDateTime checkInTime) {
            this.checkIn = checkInTime;
            return this;
        }

        public Builder setCheckOutTime(LocalDateTime checkOutTime) {
            this.checkOut = checkOutTime;
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

        public Builder setCancellationDate(LocalDate cancellationDate) {
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

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }

    @Override
    public String toString() {
        return "bookingId=" + bookingId +
                ", user=" + user +
                ", room=" + room +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", totalAmount=" + totalAmount +
                ", bookingStatus=" + bookingStatus +
                ", cancellationDate=" + cancellationDate +
                ", refundAmount=" + refundAmount +
                ", gstRates=" + gstRates +
                ", numberOfGuests=" + numberOfGuests +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt;
    }
}
