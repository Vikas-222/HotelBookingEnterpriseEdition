package com.example.model;

import com.example.common.enums.RoomType;

import java.time.LocalDateTime;

public class Room {

    private int roomId;
    private int roomNumber;
    private RoomType roomType;
    private int capacity;
    private float pricePerNight;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Room() {
    }

    public Room(Builder builder) {
        this.roomId = builder.roomId;
        this.roomNumber = builder.roomNumber;
        this.roomType = builder.roomType;
        this.capacity = builder.capacity;
        this.pricePerNight = builder.pricePerNight;
        this.active = builder.active;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getPricePerNight() {
        return pricePerNight;
    }

    public boolean getIsActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder {
        private int roomId;
        private int roomNumber;
        private RoomType roomType;
        private int capacity;
        private float pricePerNight;
        private boolean active;

        public Builder setRoomId(int roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder setRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder setRoomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setPricePerNight(float pricePerNight) {
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }

    @Override
    public String toString() {
        return "roomId=" + roomId +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", isActive=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt;
    }
}
