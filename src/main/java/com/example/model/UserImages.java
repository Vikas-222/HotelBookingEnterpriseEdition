package com.example.model;

import java.time.LocalDateTime;

public class UserImages {

    private int id;
    private String imagePath;
    private int userId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public UserImages(String imagePath, int userId) {
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public UserImages(int id, String imagePath, int userId) {
        this.id = id;
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", userId=" + userId +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at;
    }
}
