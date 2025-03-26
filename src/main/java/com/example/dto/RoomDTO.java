package com.example.dto;

import com.example.common.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonDeserialize(builder = RoomDTO.Builder.class)
public class RoomDTO {
    private int roomId;
    private int roomNumber;
    private RoomType roomType;
    private int capacity;
    private float pricePerNight;
    private String imagePath;
    private boolean isActive;

    public RoomDTO() {
    }

    private RoomDTO(Builder builder) {
        this.roomId = builder.roomId;
        this.roomNumber = builder.roomNumber;
        this.roomType = builder.roomType;
        this.capacity = builder.capacity;
        this.pricePerNight = builder.pricePerNight;
        this.imagePath = builder.imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public boolean getIsActive() {
        return isActive;
    }

    //    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private int roomId;
        private int roomNumber;
        private RoomType roomType;
        private int capacity;
        private float pricePerNight;
        private String imagePath;
        private boolean isActive;

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

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder setIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public RoomDTO build() {
            return new RoomDTO(this);
        }
    }

    @Override
    public String toString() {
        return "roomId=" + roomId +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ",imagePath=" + imagePath +
                ", isActive=" + isActive;
    }
}
