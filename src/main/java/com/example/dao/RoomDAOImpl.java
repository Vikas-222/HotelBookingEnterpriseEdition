package com.example.dao;

import com.example.common.Messages;
import com.example.db.DbConnect;
import com.example.exception.ApplicationException;
import com.example.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAOImpl implements IRoomDAO {

    @Override
    public void addRoom(Room room) throws ApplicationException {
        try {
            Connection connection = DbConnect.instance.getConnection();
            String insert = "insert into room(room_number,room_type,capacity,price_per_night,images) values (?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(insert);
            pst.setInt(1, room.getRoomNumber());
            pst.setString(2, room.getRoomType().toString());
            pst.setInt(3, room.getCapacity());
            pst.setFloat(4, room.getPricePerNight());
            pst.setString(5, room.getImagePath());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(Messages.Error.FAILED,ApplicationException.ErrorType.SYSTEM_ERROR,e);
        }
    }

    @Override
    public boolean isValidRoomNumber(int roomNumber) throws ApplicationException {
        try {
            Connection connection = DbConnect.instance.getConnection();
            String findRoom = "select * from room where room_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findRoom);
            preparedStatement.setInt(1, roomNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                throw new ApplicationException(Messages.RoomError.ROOM_EXISTS,ApplicationException.ErrorType.USER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(Messages.Error.FAILED,ApplicationException.ErrorType.SYSTEM_ERROR);
        }
        return true;
    }
}
