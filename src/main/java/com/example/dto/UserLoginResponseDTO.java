package com.example.dto;

import com.example.common.enums.Role;

public class UserLoginResponseDTO {

        private int userId;
        private String email;
        private Role role;

    public UserLoginResponseDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return  "userId=" + userId +
                ", email='" + email + '\'' +
                ", role=" + role;
    }
}
