package com.example.model;

import java.time.LocalDateTime;

public class RoomImages {
    private int id;
    private String imagePath;
    private int roomId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomImages(String imagepath, int roomId) {
        this.imagePath = imagepath;
        this.roomId = roomId;
    }

    public RoomImages() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagepath() {
        return imagePath;
    }

    public void setImagepath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", imagepath='" + imagePath + '\'' +
                ", room_id=" + roomId +
                ", created_at=" + createdAt +
                ", updated_at=" + updatedAt;
    }
}
