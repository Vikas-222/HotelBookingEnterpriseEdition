package com.example.dto;

public class LoginRequestUserDTO {

    private String email;
    private String password;

    public LoginRequestUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequestUserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "email='" + email + '\'' +
                ", password='" + password;
    }
}
