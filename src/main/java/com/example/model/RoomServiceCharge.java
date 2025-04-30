package com.example.model;

import com.example.common.enums.RoomType;

public class RoomServiceCharge {

    private RoomType roomType;
    private float chargePerNight;

    public RoomServiceCharge() {
    }

    public RoomServiceCharge(RoomType roomType, float chargePerNight) {
        this.roomType = roomType;
        this.chargePerNight = chargePerNight;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public float getChargePerNight() {
        return chargePerNight;
    }


    public void setRoomType(RoomType roomType) {
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
