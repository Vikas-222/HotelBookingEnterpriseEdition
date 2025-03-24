package com.example.common.enums;

public enum BookingStatus {
    CONFIRMED,
    CANCELLATION,
    COMPLETED;

    public static BookingStatus fromString(String status) {
        return BookingStatus.valueOf(status.toUpperCase());
    }

}
