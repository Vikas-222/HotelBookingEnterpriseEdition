package com.example.dao;

import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.util.List;

public interface IRoomDAO {

    boolean addRoom(RoomDTO room) throws DBException;

    boolean updateRoom(RoomDTO room) throws DBException;

    boolean isRoomNumberExists(int roomNumber) throws DBException;

    void updateRoomPrice(int roomNumber, Room room) throws DBException;

    void updateRoomStatus(int roomNumber, String status) throws ApplicationException;

    boolean isCapacityValid(int roomNumber, int numberOfGuests) throws DBException;

    boolean isValidRoomId(int roomId) throws DBException;

    List<RoomDTO> getAllRoomWithImage() throws DBException;

    RoomDTO getRoom(int roomId) throws DBException;

    float getGstRatesByRoomPrice(float price) throws DBException;
}
