package com.example.dto;

import com.example.common.enums.RoomType;
import com.example.dao.RoomImages;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.ArrayList;
import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(builder = RoomDTO.Builder.class)
public class RoomDTO {
    private int roomId;
    private int roomNumber;
    private RoomType roomType;
    private int capacity;
    private float pricePerNight;
    private List<RoomImages> roomImagesList;
    private boolean active;

    public RoomDTO() {
    }

    private RoomDTO(Builder builder) {
        this.roomId = builder.roomId;
        this.roomNumber = builder.roomNumber;
        this.roomType = builder.roomType;
        this.capacity = builder.capacity;
        this.pricePerNight = builder.pricePerNight;
        this.roomImagesList = builder.roomImagesList;
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

    public List<RoomImages> getRoomImagesList() {
        return roomImagesList;
    }

    public boolean getIsActive() {
        return active;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private int roomId;
        private int roomNumber;
        private RoomType roomType;
        private int capacity;
        private float pricePerNight;
        private List<RoomImages> roomImagesList = new ArrayList<>();
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

        public Builder setRoomImagesList(List<RoomImages> roomImagesList) {
            this.roomImagesList = roomImagesList;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
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
                ", roomImagesList=" + roomImagesList +
                ", active=" + active;
    }
}
