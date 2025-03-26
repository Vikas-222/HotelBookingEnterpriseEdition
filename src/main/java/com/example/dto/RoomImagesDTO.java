package com.example.dto;

public class RoomImagesDTO {
        private int id;
        private String imagePath;
        private int roomId;

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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", roomId=" + roomId;
    }
}
