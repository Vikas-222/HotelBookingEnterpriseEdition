package com.example.entity;

public class Room {

    private int roomId;
    private int roomNumber;
    private RoomType roomType;
    private int capacity;
    private float pricePerNight;
    private String imagePath;
    private boolean isActive;

    public enum RoomType {
        DELUXE,
        SINGLEBED,
        DOUBLEBED,
        TRIPLE,
        QUEEN,
        KING
    }

    public Room(int roomNumber, int capacity,RoomType roomType, float pricePerNight, String imagePath) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.imagePath = imagePath;
    }

    public Room() {}

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

    @Override
    public String toString() {
        return "roomId=" + roomId +
                ", roomNumber=" + roomNumber +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", imagePath='" + imagePath + '\'' +
                ", isActive=" + isActive;
    }
}

