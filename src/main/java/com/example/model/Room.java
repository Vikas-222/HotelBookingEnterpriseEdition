package com.example.model;

import com.example.common.enums.RoomType;

import java.time.LocalDateTime;

public class Room {

    private int roomId;
    private int roomNumber;
    private RoomType roomType;
    private int capacity;
    private float pricePerNight;
    private String imagePath;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Room(int roomNumber, int capacity, RoomType roomType, float pricePerNight, String imagePath) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.imagePath = imagePath;
    }

    public Room() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
        return "roomId=" + roomId +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", imagePath='" + imagePath + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt;
    }
}

