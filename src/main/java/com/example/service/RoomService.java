package com.example.service;

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

    public boolean isRoomNumberExists(int roomNumber) throws DBException {
        System.out.println(iRoomDAO.isRoomNumberExists(roomNumber));
        return iRoomDAO.isRoomNumberExists(roomNumber);
    }

    public void updateRoomPrice(int roomNumber,RoomDTO roomDTO) throws DBException {
        Room room = RoomMapper.convertRoomDTOToRoom(roomDTO);
        iRoomDAO.updateRoomPrice(roomNumber,room);
    }

    public void updateRoomStatus(RoomDTO roomDTO) throws ApplicationException {
        Room room = RoomMapper.convertRoomDTOToUpdateRoom(roomDTO);
        iRoomDAO.updateRoomStatus(room.getRoomNumber(),room.getIsActive());
    }

    public List<RoomDTO> getAllRooms() throws DBException{
        return RoomMapper.convertRoomListToRoomDTOList(iRoomDAO.getAllRooms());
    }

    public void saveImagePathsToDatabase(List<String> imagePaths, int roomId) throws DBException {
        iRoomDAO.saveImagePathsToDatabase(imagePaths,roomId);
    }
}
