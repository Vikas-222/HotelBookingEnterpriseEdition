package com.example.common.enums;

public enum Role {
    ADMIN,
    USER;

    public static Role fromString(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}
