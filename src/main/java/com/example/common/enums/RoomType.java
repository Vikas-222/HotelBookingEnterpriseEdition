package com.example.common.enums;

public enum RoomType {
    DELUXE,
    SINGLEBED,
    DOUBLEBED,
    TRIPLEBED,
    QUEEN,
    KING;

    public static RoomType fromString(String roomType) {
        return RoomType.valueOf(roomType.toUpperCase());
    }
}
