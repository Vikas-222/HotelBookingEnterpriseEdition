package com.example.model;

public class RoomServiceCharge {
    private String roomType;
    private float chargePerNight;

    public RoomServiceCharge() {
    }

    public RoomServiceCharge(String roomType, float chargePerNight) {
        this.roomType = roomType;
        this.chargePerNight = chargePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public float getChargePerNight() {
        return chargePerNight;
    }


    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setChargePerNight(float chargePerNight) {
        this.chargePerNight = chargePerNight;
    }

    @Override
    public String toString() {
        return "RoomServiceCharge{" +
                "roomType='" + roomType + '\'' +
                ", chargePerNight=" + chargePerNight +
                '}';
    }

}
