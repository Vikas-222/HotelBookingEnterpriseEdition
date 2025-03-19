package com.example.dao;

import com.example.common.Messages;
import com.example.common.exception.DBException;
import com.example.dbconfig.DbConnect;
import com.example.common.exception.ApplicationException;
import com.example.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAOImpl implements IRoomDAO {

    @Override
    public void addRoom(Room room) throws DBException {
        String insert = "insert into room(room_number,room_type,capacity,price_per_night,images) values (?,?,?,?,?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(insert);) {
            pst.setInt(1, room.getRoomNumber());
            pst.setString(2, room.getRoomType().toString());
            pst.setInt(3, room.getCapacity());
            pst.setFloat(4, room.getPricePerNight());
            pst.setString(5, room.getImagePath());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Messages.Error.FAILED, e);
        }
    }

    @Override
    public boolean isRoomNumberExists(int roomNumber) throws DBException {
        String findRoom = "select * from room where room_number = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findRoom);) {
            preparedStatement.setInt(1, roomNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Messages.Error.FAILED,e);
        }
        return true;
    }
}
