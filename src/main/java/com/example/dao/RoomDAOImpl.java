package com.example.dao;

import com.example.common.Messages;
import com.example.common.enums.RoomType;
import com.example.common.exception.DBException;
import com.example.config.DbConnect;
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
    public int addRoom(Room room) throws DBException {
        String insert = "insert into room(room_number,room_type,capacity,price_per_night) values (?,?,?,?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(insert)) {
            pst.setInt(1, room.getRoomNumber());
            pst.setString(2, room.getRoomType().toString());
            pst.setInt(3, room.getCapacity());
            pst.setFloat(4, room.getPricePerNight());
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println(pst.getGeneratedKeys());
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }


    @Override
    public boolean isRoomNumberExists(int roomNumber) throws DBException {
        String findRoom = "select * from room where room_number = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findRoom)) {
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
    public void updateRoomPrice(int roomNumber, Room room) throws DBException {
        String findRoom = "select * from room where roomNumber = ?";
        String updatePrice = "update room set capacity = ? and price = ? where room_number = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstFind = connection.prepareStatement(findRoom);
             PreparedStatement pstUpdate = connection.prepareStatement(updatePrice)) {
            pstFind.setInt(1, roomNumber);
            rs = pstFind.executeQuery();

//            if (!rs.next()) {
//                throw new DBException("Room with number " + roomNumber + " does not exist.");
//            } this validation should be check on controller and throw ApplicaExcep
            pstUpdate.setInt(1, room.getCapacity());
            pstUpdate.setFloat(2, room.getPricePerNight());
            pstUpdate.setInt(3, roomNumber);
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateRoomStatus(int roomNumber, boolean status) throws ApplicationException {
        String findRoom = "select * from room where room_number = ?";
        String updateStatus = "update room set is_active = ? where room_number = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstFind = connection.prepareStatement(findRoom);
             PreparedStatement pstUpdate = connection.prepareStatement(updateStatus)) {
            pstFind.setInt(1, roomNumber);
            rs = pstFind.executeQuery();
            if (!rs.next()) {
                throw new ApplicationException("Room with number " + roomNumber + " does not exist.");
            }
            pstUpdate.setBoolean(1, status);
            pstUpdate.setInt(2, roomNumber);
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } catch (ApplicationException e) {
            throw e;
        }
    }

    @Override
    public List<Room> getAllRooms() throws DBException {
        String fetch = "select * from room";
        ResultSet rs = null;
        List<Room> roomList = new ArrayList<>();
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstFind = connection.prepareStatement(fetch)) {
            rs = pstFind.executeQuery();
            while (rs.next()) {
                String roomType = rs.getString("room_type");
                Room room = new Room.Builder().setRoomId(rs.getInt("room_id")).setRoomNumber(rs.getInt("room_number"))
                        .setRoomType(RoomType.fromString(roomType)).setCapacity(rs.getInt("capacity"))
                        .setPricePerNight(rs.getFloat("price_per_night")).setActive(rs.getBoolean("is_active")).build();
                roomList.add(room);
            }
            return roomList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Messages.Error.FAILED);
        }
    }

    @Override
    public void saveImagePathsToDatabase(List<String> imagePaths, int roomId) throws DBException {
        String query = "INSERT INTO room_images (imagepath, room_id) VALUES (?, ?)";
        try (Connection conn = DbConnect.instance.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);) {
            for (String imagePath : imagePaths) {
                pst.setString(1, imagePath);
                pst.setInt(2, roomId);
                pst.addBatch();
            }
            pst.executeBatch();

        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}

