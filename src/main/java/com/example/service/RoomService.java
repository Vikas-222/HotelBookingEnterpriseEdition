package com.example.service;

import com.example.common.Messages;
import com.example.common.enums.RoomStatus;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.RoomMapper;
import com.example.controller.validation.RoomValidator;
import com.example.dao.IRoomDAO;
import com.example.dao.entity.RoomDAO;
import com.example.dao.impl.RoomDAOImpl;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.util.List;

public class RoomService {

    private IRoomDAO iRoomDAO = new RoomDAO();

    public boolean addRoom(RoomDTO roomDTO) throws ApplicationException {
        RoomValidator.roomValidate(roomDTO);
        isRoomNumberExists(roomDTO.getRoomNumber());
        return iRoomDAO.addRoom(roomDTO);
    }

    public boolean updateRoom(RoomDTO room,String roomId) throws ApplicationException {
        if(roomId.isBlank()){
            throw new ApplicationException(Messages.RoomError.ROOM_ID_NOT_FOUND);
        }
        int roomID = Integer.parseInt(roomId);
        if(roomID <= 0){
            throw new ApplicationException(Messages.RoomError.ROOM_ID_NOT_FOUND);
        }
        isValidRoomId(roomID);
        RoomDTO roomDTO = new RoomDTO.Builder().setRoomId(roomID)
                .setRoomNumber(room.getRoomNumber())
                .setRoomType(room.getRoomType())
                .setCapacity(room.getCapacity())
                .setPricePerNight(room.getPricePerNight())
                .setRoomStatus(room.getRoomStatus())
                .setImagePath(room.getImagePath()).build();
        return iRoomDAO.updateRoom(roomDTO);
    }

    public void isRoomNumberExists(int roomNumber) throws ApplicationException {
        if (iRoomDAO.isRoomNumberExists(roomNumber)) {
            throw new ApplicationException(Messages.RoomError.ROOM_NUMBER_EXISTS);
        }
    }

    public void updateRoomPrice(int roomNumber, RoomDTO roomDTO) throws ApplicationException {
        Room room = RoomMapper.convertRoomDTOToRoom(roomDTO);
        isRoomNumberExists(room.getRoomNumber());
        iRoomDAO.updateRoomPrice(roomNumber, room);
    }

    public void updateRoomStatus(int roomId, RoomStatus status) throws ApplicationException {
        try {
            if (status == null) {
                throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
            }
            isValidRoomId(roomId);
            iRoomDAO.updateRoomStatus(roomId,status);
        } catch (ApplicationException e) {
            throw e;
        }
    }

    public List<RoomDTO> getAllRooms() throws DBException {
        return iRoomDAO.getAllRoomWithImage();
    }

    public boolean isCapacityValid(int roomId, int numberOfGuest) throws ApplicationException {
        if (iRoomDAO.isCapacityValid(roomId, numberOfGuest) == false) {
            throw new ApplicationException(Messages.BookingError.CAPACITY_EXCEEDED);
        }
        return true;
    }

    public void isValidRoomId(int roomId) throws ApplicationException {
        if (iRoomDAO.isValidRoomId(roomId) == false) {
            throw new ApplicationException(Messages.RoomError.ROOM_ID_NOT_FOUND);
        }
    }

    public RoomDTO getRoomDetails(int roomId) throws ApplicationException {
        isValidRoomId(roomId);
        return iRoomDAO.getRoomById(roomId);
    }

    public float getGstRatesByRoomPrice(float price) throws ApplicationException {
        if(price <= 0){
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_PRICE);
        }
        return iRoomDAO.getGstRatesByRoomPrice(price);
    }

}
