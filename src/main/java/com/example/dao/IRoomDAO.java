package com.example.dao;

import com.example.exception.ApplicationException;
import com.example.model.Room;

public interface IRoomDAO {

    void addRoom(Room room) throws ApplicationException;
    boolean isValidRoomNumber(int roomNumber) throws ApplicationException;

}
