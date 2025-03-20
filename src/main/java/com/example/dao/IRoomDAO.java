package com.example.dao;

import com.example.common.exception.DBException;
import com.example.model.Room;

import java.util.List;

public interface IRoomDAO {

    void addRoom(Room room) throws DBException;
    boolean isRoomNumberExists(int roomNumber) throws DBException;
    void updateRoomPrice(int roomNumber,Room room) throws DBException;
    void updateRoomStatus(int roomNumber,boolean status) throws DBException;
    List<Room> getAllRooms() throws DBException;

}
