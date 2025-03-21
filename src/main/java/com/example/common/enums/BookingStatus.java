package com.example.common.enums;

public enum BookingStatus {
    CONFIRMED,
    CANCELLATION;

    public static BookingStatus fromString(String status) {
        return BookingStatus.valueOf(status.toUpperCase());
    }

}
