package com.example.dao;

import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.model.Room;

public interface IRoomDAO {

    void addRoom(Room room) throws DBException;
    boolean isRoomNumberExists(int roomNumber) throws DBException;


}
