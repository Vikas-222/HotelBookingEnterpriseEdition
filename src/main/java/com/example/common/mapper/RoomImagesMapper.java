package com.example.common.mapper;

import com.example.dto.RoomImagesDTO;
import com.example.model.RoomImages;

public class RoomImagesMapper {

    public static RoomImages convertRoomImagesDTOToEntity(RoomImagesDTO roomImagesDTO){
        RoomImages roomImages = new RoomImages();
        roomImages.setRoomId(roomImagesDTO.getRoomId());
        roomImages.setImagepath(roomImagesDTO.getImagePath());
        return roomImages;
    }
}
