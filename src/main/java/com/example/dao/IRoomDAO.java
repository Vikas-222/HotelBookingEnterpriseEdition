package com.example.dao;

import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.util.List;
import java.util.Map;

public interface IRoomDAO {

    int addRoom(Room room) throws DBException;

    boolean isRoomNumberExists(int roomNumber) throws DBException;

    void updateRoomPrice(int roomNumber, Room room) throws DBException;

    void updateRoomStatus(int roomNumber, String status) throws ApplicationException;

    boolean isCapacityValid(int roomNumber, int numberOfGuests) throws DBException;

    float getRoomPrice(int roomNumber) throws DBException;

    boolean isValidRoomId(int roomId) throws DBException;

    List<RoomDTO> getAllRoomWithImage() throws DBException;

    Room getRoom(int roomId) throws DBException;
}
