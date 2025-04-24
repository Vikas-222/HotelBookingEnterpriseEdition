package com.example.entitymodal;

import jakarta.persistence.*;

@Entity
@Table(name = "room_service_charge")
public class RoomServiceCharge {

    @Enumerated(EnumType.STRING)
    @Id
    @Column(name = "room_type")
    private String roomType;

    @Column(name = "charge_per_night", nullable = false)
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

