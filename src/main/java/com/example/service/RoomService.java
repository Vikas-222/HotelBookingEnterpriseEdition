package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.RoomMapper;
import com.example.dao.IRoomDAO;
import com.example.dao.impl.RoomDAOImpl;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.util.List;

public class RoomService {

    private IRoomDAO iRoomDAO = new RoomDAOImpl();

    public boolean addRoom(RoomDTO roomDTO) throws DBException {
//        Room room = RoomMapper.convertRoomDTOToRoom(roomDTO);
        return iRoomDAO.addRoom(roomDTO);
    }

    public boolean isRoomNumberExists(int roomNumber) throws ApplicationException {
        if (iRoomDAO.isRoomNumberExists(roomNumber) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        return true;
    }

    public void updateRoomPrice(int roomNumber, RoomDTO roomDTO) throws ApplicationException {
        Room room = RoomMapper.convertRoomDTOToRoom(roomDTO);
        if (iRoomDAO.isRoomNumberExists(room.getRoomNumber())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        iRoomDAO.updateRoomPrice(roomNumber, room);
    }

    public void updateRoomStatus(int roomId,String status) throws ApplicationException {
        try {
            if (status.isBlank()) {
                throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
            }
            if (!iRoomDAO.isValidRoomId(roomId)) {
                throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
            }
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

    public float getRoomPriceByRoomId(int roomId) throws ApplicationException {
        if (iRoomDAO.isValidRoomId(roomId) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_ID);
        }
        return iRoomDAO.getRoomPriceByRoomId(roomId);
    }

    public boolean isValidRoomId(int roomId) throws ApplicationException {
        if (iRoomDAO.isValidRoomId(roomId) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_ID);
        }
        return true;
    }

    public RoomDTO getRoomDetails(int roomId) throws ApplicationException {
        if (iRoomDAO.isValidRoomId(roomId) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_ID);
        }
        return RoomMapper.convertRoomToRoomDTO(iRoomDAO.getRoom(roomId));
    }

    public float getGstRateByRoomPrice(float price) throws ApplicationException {
        if(price <= 0){
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_PRICE);
        }
        return iRoomDAO.getGstRatesByRoomPrice(price);
    }

}
