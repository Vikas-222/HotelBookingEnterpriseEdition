package com.example.common.enums;

public enum RoomStatus {
    AVAILABLE,
    OCCUPIED,
    UNDER_MAINTENANCE;

    public static RoomStatus fromString(String roomStatus) {
        return RoomStatus.valueOf(roomStatus.toUpperCase());
    }
}
