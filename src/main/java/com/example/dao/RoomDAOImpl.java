package com.example.dao;

import com.example.common.Messages;
import com.example.common.enums.RoomType;
import com.example.common.exception.DBException;
import com.example.dbconfig.DbConnect;
import com.example.common.exception.ApplicationException;
import com.example.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements IRoomDAO {

    @Override
    public void addRoom(Room room) throws DBException {
        String insert = "insert into room(room_number,room_type,capacity,price_per_night) values (?,?,?,?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(insert);) {
            pst.setInt(1, room.getRoomNumber());
            pst.setString(2, room.getRoomType().toString());
            pst.setInt(3, room.getCapacity());
            pst.setFloat(4, room.getPricePerNight());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
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
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public void updateRoomPrice(int roomNumber,Room room) throws DBException {
        String findRoom = "select * from room where roomNumber = ?";
        String updatePrice = "update room set capacity = ? and price = ? where room_number = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstFind = connection.prepareStatement(findRoom);
             PreparedStatement pstUpdate = connection.prepareStatement(updatePrice);) {
            pstFind.setInt(1, roomNumber);
            rs = pstFind.executeQuery();

//            if (!rs.next()) {
//                throw new DBException("Room with number " + roomNumber + " does not exist.");
//            } this validation should be check on controller and throw ApplicaExcep
            pstUpdate.setInt(1,room.getCapacity());
            pstUpdate.setFloat(2,room.getPricePerNight());
            pstUpdate.setInt(3,roomNumber);
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateRoomStatus(int roomNumber, boolean status) throws DBException {
        String findRoom = "select * from room where roomNumber = ?";
        String updatePrice = "update room set status = ? where room_number = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstFind = connection.prepareStatement(findRoom);
             PreparedStatement pstUpdate = connection.prepareStatement(updatePrice);) {
            pstFind.setInt(1, roomNumber);
            rs = pstFind.executeQuery();

            if (!rs.next()) {
                throw new DBException("Room with number " + roomNumber + " does not exist.");
            }
            pstUpdate.setBoolean(1,status);
            pstUpdate.setInt(2,roomNumber);
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Messages.Error.FAILED);
        }
    }

    @Override
    public List<Room> getAllRooms() throws DBException {
        String fetch = "select * from room";
        ResultSet rs = null;
        List<Room> roomList = new ArrayList<>();
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstFind = connection.prepareStatement(fetch);) {
            rs = pstFind.executeQuery();
                while (rs.next()) {
                    String roomType = rs.getString("room_type");
                    Room room = new Room.Builder().roomId(rs.getInt("room_id")).roomNumber(rs.getInt("room_number"))
                            .roomType(RoomType.fromString(roomType)).capacity(rs.getInt("capacity"))
                            .pricePerNight(rs.getFloat("price_per_night")).isActive(rs.getBoolean("is_active")).build();
                    roomList.add(room);
                }
                return roomList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Messages.Error.FAILED);
        }
    }
}
