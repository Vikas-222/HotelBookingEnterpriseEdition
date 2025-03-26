package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.RoomMapper;
import com.example.dao.IRoomDAO;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.util.List;

public class RoomService {

    private final IRoomDAO iRoomDAO;
    public RoomService(IRoomDAO iRoomDAO){
        this.iRoomDAO = iRoomDAO;
    }

    public int addRoom(RoomDTO roomDTO) throws DBException {
        Room room = RoomMapper.convertRoomDTOToRoom(roomDTO);
        System.out.println("service "+room);
        return iRoomDAO.addRoom(room);
    }

    public boolean isRoomNumberExists(int roomNumber) throws ApplicationException {
        if(iRoomDAO.isRoomNumberExists(roomNumber) == false){
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        return iRoomDAO.isRoomNumberExists(roomNumber);
    }

    public void updateRoomPrice(int roomNumber,RoomDTO roomDTO) throws ApplicationException {
        Room room = RoomMapper.convertRoomDTOToRoom(roomDTO);
        if(iRoomDAO.isRoomNumberExists(room.getRoomNumber())){
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        iRoomDAO.updateRoomPrice(roomNumber,room);
    }

    public void updateRoomStatus(RoomDTO roomDTO) throws ApplicationException {
        Room room = RoomMapper.convertRoomDTOToUpdateRoom(roomDTO);
        if(!iRoomDAO.isRoomNumberExists(roomDTO.getRoomNumber())){
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        iRoomDAO.updateRoomStatus(room.getRoomNumber(),room.getIsActive());
    }

    public List<RoomDTO> getAllRooms() throws DBException{
        return iRoomDAO.getAllRoomWithImage();
    }

    public boolean isCapacityValid(int roomNumber,int numberOfGuest) throws ApplicationException {
        if(!iRoomDAO.isRoomNumberExists(roomNumber)){
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        if(!iRoomDAO.isCapacityValid(roomNumber,numberOfGuest)){
            throw new ApplicationException(Messages.BookingError.INVALID_CAPACITY);
        }
        return iRoomDAO.isCapacityValid(roomNumber,numberOfGuest);
    }

    public float getRoomPrice(int roomNumber) throws ApplicationException {
        isRoomNumberExists(roomNumber);
        return iRoomDAO.getRoomPrice(roomNumber);
    }
}
