package com.example.common.enums;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender fromString(String gender) {
        return Gender.valueOf(gender.toUpperCase());
    }
}
