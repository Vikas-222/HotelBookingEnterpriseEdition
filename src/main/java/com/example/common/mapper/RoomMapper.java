package com.example.common.mapper;

import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.util.stream.Collectors;

public class RoomMapper {

    public static Room convertRoomDTOToRoom(RoomDTO roomDTO) {
        return new Room.Builder()
                .setRoomNumber(roomDTO.getRoomNumber())
                .setRoomType(roomDTO.getRoomType())
                .setCapacity(roomDTO.getCapacity())
                .setPricePerNight(roomDTO.getPricePerNight())
                .build();
    }

    public static RoomDTO convertEntityToRoomDTO(Room room,float roomServiceCharge) {
        return new RoomDTO.Builder()
                .setRoomId(room.getRoomId())
                .setRoomNumber(room.getRoomNumber())
                .setRoomType(room.getRoomType())
                .setCapacity(room.getCapacity())
                .setPricePerNight(room.getPricePerNight())
                .setRoomStatus(room.getRoomStatus())
                .setRoomServiceCharge(roomServiceCharge)
                .setImagePath(room.getRoomImages().stream()
                        .map(item -> item.getImagepath())
                        .collect(Collectors.toList()))
                .build();
    }
}
