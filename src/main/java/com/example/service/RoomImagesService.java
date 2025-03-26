package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.mapper.RoomImagesMapper;
import com.example.dao.IRoomDAO;
import com.example.dao.IRoomImagesDAO;
import com.example.dto.RoomImagesDTO;
import com.example.model.RoomImages;

import java.util.List;

public class RoomImagesService {

    IRoomImagesDAO iRoomImagesDAO;
    IRoomDAO iRoomDAO;

    public RoomImagesService(IRoomImagesDAO iRoomImagesDAO, IRoomDAO iRoomDAO) {
        this.iRoomImagesDAO = iRoomImagesDAO;
        this.iRoomDAO = iRoomDAO;
    }

    public RoomImagesService(IRoomImagesDAO iRoomImagesDAO) {
        this.iRoomImagesDAO = iRoomImagesDAO;
    }

    public void saveRoomImage(RoomImagesDTO roomImagesDTO) throws ApplicationException {
        if (iRoomDAO.isValidRoomId(roomImagesDTO.getRoomId()) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_ID);
        }
        RoomImages roomImages = RoomImagesMapper.convertRoomImagesDTOToEntity(roomImagesDTO);
        iRoomImagesDAO.saveRoomImage(roomImages);
    }

    public List<RoomImages> getRoomImagesByRoomId(int roomId) throws ApplicationException {
        if(String.valueOf(roomId).isBlank() || String.valueOf(roomId) == null){
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_ID);
        }
        if (roomId <= 0) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_ID);
        }
        return iRoomImagesDAO.getRoomImagesByRoomId(roomId);
    }


}
