package com.example.dto;

import com.example.common.enums.RoomStatus;
import com.example.common.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {
    private int roomId;
    private int roomNumber;
    private RoomType roomType;
    private int capacity;
    private float pricePerNight;
    private List<String> imagePath;
    private String imagePaths;
    private RoomStatus roomStatus;

    public RoomDTO() {
    }


    private RoomDTO(Builder builder) {
        this.roomId = builder.roomId;
        this.roomNumber = builder.roomNumber;
        this.roomType = builder.roomType;
        this.capacity = builder.capacity;
        this.pricePerNight = builder.pricePerNight;
        this.imagePath = builder.imagePath;
        this.imagePaths = builder.imagePaths;
        this.roomStatus = builder.roomStatus;
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

    public List<String> getImagePath() {
        return imagePath;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public String getImagePaths() {
        return imagePaths;
    }

    public static class Builder {

        private int roomId;
        private int roomNumber;
        private RoomType roomType;
        private int capacity;
        private float pricePerNight;
        private List<String> imagePath = new ArrayList<>();
        private String imagePaths;
        private RoomStatus roomStatus;

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

        public Builder setImagePath(List<String> imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder setImagePaths(String imagePaths) {
            this.imagePaths = imagePaths;
            return this;
        }

        public Builder setRoomStatus(RoomStatus roomStatus) {
            this.roomStatus = roomStatus;
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
                ",imagePaths=" + imagePaths +
                ", roomStatus=" + roomStatus;
    }
}
