package com.example.dao;

import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.util.List;

public interface IRoomDAO {

    int addRoom(Room room) throws DBException;

    boolean isRoomNumberExists(int roomNumber) throws DBException;

    void updateRoomPrice(int roomNumber, Room room) throws DBException;

    void updateRoomStatus(int roomNumber, boolean status) throws ApplicationException;

//    List<Room> getAllRooms() throws DBException;

    boolean isCapacityValid(int roomNumber, int numberOfGuests) throws DBException;

    float getRoomPrice(int roomNumber) throws DBException;

    boolean isValidRoomId(int roomId) throws DBException;

    List<RoomDTO> getAllRoomWithImage() throws DBException;
}
