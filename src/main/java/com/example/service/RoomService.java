package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.RoomMapper;
import com.example.dao.IRoomDAO;
import com.example.dao.impl.RoomDAOImpl;
import com.example.dto.RoomDTO;
import com.example.model.Room;
import com.example.controller.validation.RoomValidator;
import java.util.List;

public class RoomService {

    private IRoomDAO iRoomDAO = new RoomDAOImpl();
    RoomValidator roomValidator = new RoomValidator();

//    public RoomService(IRoomDAO iRoomDAO) {
//        this.iRoomDAO = iRoomDAO;
//    }

    public int addRoom(RoomDTO roomDTO) throws DBException {
        Room room = RoomMapper.convertRoomDTOToRoom(roomDTO);
        System.out.println("service " + room);
        return iRoomDAO.addRoom(room);
    }

    public boolean isRoomNumberExists(int roomNumber) throws ApplicationException {
        if (iRoomDAO.isRoomNumberExists(roomNumber) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        return iRoomDAO.isRoomNumberExists(roomNumber);
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
        return iRoomDAO.isCapacityValid(roomId, numberOfGuest);
    }

    public float getRoomPrice(int roomNumber) throws ApplicationException {
        isRoomNumberExists(roomNumber);
        return iRoomDAO.getRoomPrice(roomNumber);
    }

    public boolean isValidRoomId(int roomId) throws ApplicationException {
        if (iRoomDAO.isValidRoomId(roomId) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_ID);
        }
        return iRoomDAO.isValidRoomId(roomId);
    }
}
