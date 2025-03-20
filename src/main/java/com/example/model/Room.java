package com.example.model;

import com.example.common.enums.RoomType;
import com.example.dao.RoomImages;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Room {

    private int roomId;
    private int roomNumber;
    private RoomType roomType;
    private int capacity;
    private float pricePerNight;
    private List<RoomImages> roomImagesList;
    private boolean isActive;
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
        this.roomImagesList = builder.roomImagesList;
        this.isActive = builder.isActive;
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

    public List<RoomImages> getRoomImagesList() {
        return roomImagesList;
    }

    public boolean isActive() {
        return isActive;
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
        private List<RoomImages> roomImagesList = new ArrayList<>();
        private boolean isActive = true;

        public Builder roomId(int roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder roomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder roomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder pricePerNight(float pricePerNight) {
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder roomImagesList(List<RoomImages> roomImagesList) {
            this.roomImagesList = roomImagesList;
            return this;
        }

        public Builder addRoomImage(RoomImages roomImage) {
            this.roomImagesList.add(roomImage);
            return this;
        }

        public Builder isActive(boolean isActive) {
            this.isActive = isActive;
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
                ", roomImagesList=" + roomImagesList +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt;
    }
}
