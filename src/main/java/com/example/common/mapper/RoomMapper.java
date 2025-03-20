package com.example.common.mapper;

import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.util.List;
import java.util.stream.Collectors;

public class RoomMapper {

    public static Room convertRoomDTOToRoom(RoomDTO roomDTO) {
        return new Room.Builder().roomNumber(roomDTO.getRoomNumber()).roomType(roomDTO.getRoomType()).capacity(roomDTO.getCapacity())
                .pricePerNight(roomDTO.getPricePerNight()).build();
    }

    public static List<RoomDTO> convertRoomListToRoomDTOList(List<Room> list) {
        return list.stream().map(room -> new RoomDTO.Builder()
                        .roomId(room.getRoomId())
                        .roomNumber(room.getRoomNumber())
                        .roomType(room.getRoomType())
                        .pricePerNight(room.getPricePerNight())
                        .capacity(room.getCapacity())
                        .setIsActive(room.isActive()).build())
                        .collect(Collectors.toList());
    }

    public static Room convertRoomDTOToUpdateRoom(RoomDTO roomDTO){
        return new Room.Builder().roomNumber(roomDTO.getRoomNumber()).isActive(roomDTO.isActive()).build();
    }

}
