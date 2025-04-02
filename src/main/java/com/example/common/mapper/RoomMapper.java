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

//    public static List<RoomDTO> convertRoomListToRoomDTOList(List<Room> list) {
//        return list.stream().map(room -> new RoomDTO.Builder()
//                        .setRoomId(room.getRoomId())
//                        .setRoomNumber(room.getRoomNumber())
//                        .setRoomType(room.getRoomType())
//                        .setPricePerNight(room.getPricePerNight())
//                        .setCapacity(room.getCapacity())
//                        .setRoomStatus(room.getRoomStatus()).build())
//                .collect(Collectors.toList());
//    }

}
