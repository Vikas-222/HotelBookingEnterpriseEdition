package com.example.dto;

import com.example.common.enums.RoomType;

public class RoomDTO {

    private int roomId;
    private int roomNumber;
    private RoomType roomType;
    private int capacity;
    private float pricePerNight;
    private String imagePath;
    private boolean isActive;

    public RoomDTO(int roomId, int roomNumber, RoomType roomType, int capacity, float pricePerNight, String imagePath) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.imagePath = imagePath;
        this.isActive = true;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
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

    @Override
    public String toString() {
        return  "roomId=" + roomId +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", imagePath='" + imagePath + '\'' +
                ", isActive=" + isActive;
    }
}
