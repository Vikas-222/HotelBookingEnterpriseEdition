package com.example.entitymodal;

import com.example.common.enums.RoomType;
import jakarta.persistence.*;

@Entity
@Table(name = "room_service_charge")
public class RoomServiceCharge {

    @Enumerated(EnumType.STRING)
    @Id
    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "charge_per_night", nullable = false)
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

