package com.example.common.enums;

public enum RoomType {
    DELUXE,
    SINGLEBED,
    DOUBLEBED,
    TRIPLE,
    QUEEN,
    KING;

    public static RoomType fromString(String roomType) {
        return RoomType.valueOf(roomType.toUpperCase());
    }
}
