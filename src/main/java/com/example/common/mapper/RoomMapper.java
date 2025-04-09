package com.example.common.mapper;

import com.example.dto.RoomDTO;
import com.example.model.Room;

public class RoomMapper {

    public static Room convertRoomDTOToRoom(RoomDTO roomDTO) {
        return new Room.Builder()
                .setRoomNumber(roomDTO.getRoomNumber())
                .setRoomType(roomDTO.getRoomType())
                .setCapacity(roomDTO.getCapacity())
                .setPricePerNight(roomDTO.getPricePerNight())
                .build();
    }
}
