package com.example.model;

import java.time.LocalDateTime;

public class RoomImages {
    private int id;
    private String imagepath;
    private int room_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public RoomImages(int id, String imagepath, int room_id) {
        this.id = id;
        this.imagepath = imagepath;
        this.room_id = room_id;
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
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", imagepath='" + imagepath + '\'' +
                ", room_id=" + room_id +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at;
    }
}
