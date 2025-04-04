package com.example.dao.impl;

import com.example.common.enums.RoomStatus;
import com.example.common.enums.RoomType;
import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dao.IRoomDAO;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
        return false;
    }

    @Override
    public void updateRoomPrice(int roomNumber, Room room) throws DBException {
        String updatePrice = "update room set capacity = ? and price_per_night = ? where room_number = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(updatePrice)) {
            pstUpdate.setInt(1, room.getCapacity());
            pstUpdate.setFloat(2, room.getPricePerNight());
            pstUpdate.setInt(3, roomNumber);
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateRoomStatus(int roomNumber, String status) throws DBException {
        String updateStatus = "update room set room_status = ? where room_number = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(updateStatus)) {
            pstUpdate.setString(1,status);
            pstUpdate.setInt(2, roomNumber);
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean isCapacityValid(int roomId, int numberOfGuests) throws DBException {
        String sql = "SELECT capacity FROM room WHERE room_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setInt(1, roomId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                return numberOfGuests <= capacity;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("dao " + e.getMessage());
            throw new DBException(e);
        }
        return false;
    }

    @Override
    public float getRoomPrice(int roomNumber) throws DBException {
        String sql = "SELECT PRICE_PER_NIGHT FROM room WHERE ROOM_NUMBER = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setInt(1, roomNumber);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getFloat("price_per_night");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
        return 0;
    }

    @Override
    public boolean isValidRoomId(int roomId) throws DBException {
        String sql = "Select * from room where room_id = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, roomId);
            rs = pst.executeQuery();
            return rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<RoomDTO> getAllRoomWithImage() throws DBException {
        String sql = "select r.room_id,r.room_number,r.room_type,r.capacity,r.price_per_night,r.room_status,im.imagepath from room r, " +
                "room_images im where r.room_id = im.room_id and r.room_status = 'AVAILABLE'";
        ResultSet rs = null;
        Map<Integer, RoomDTO> roomMap = new HashMap<>();
        try (Connection connection = DbConnect.instance.getConnection();
             Statement pst = connection.createStatement()) {
            rs = pst.executeQuery(sql);
            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                String imagePath = rs.getString("imagepath");

                if (roomMap.containsKey(roomId)) {
                    roomMap.get(roomId).getImagePath().add(imagePath);
                } else {
                    List<String> newImagePathList = new ArrayList<>();
                    newImagePathList.add(imagePath);
                    RoomDTO roomDTO = new RoomDTO.Builder()
                            .setRoomId(roomId)
                            .setRoomNumber(rs.getInt("room_number"))
                            .setRoomType(RoomType.fromString(rs.getString("room_type")))
                            .setCapacity(rs.getInt("capacity"))
                            .setPricePerNight(rs.getFloat("price_per_night"))
                            .setRoomStatus(RoomStatus.fromString(rs.getString("room_status")))
                            .setImagePath(newImagePathList)
                            .build();
                    roomMap.put(roomId, roomDTO);
                }
            }
        }catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
        return new ArrayList<>(roomMap.values());
    }

    @Override
    public Room getRoom(int roomId) throws DBException {
        String sql = "SELECT * FROM room WHERE room_id = ?";
        ResultSet rs = null;
        Room room = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setInt(1, roomId);
            rs = pst.executeQuery();
            while(rs.next()){
                room = new Room.Builder()
                        .setRoomId(roomId)
                        .setRoomNumber(rs.getInt("room_number"))
                        .setRoomType(RoomType.fromString(rs.getString("room_type")))
                        .setPricePerNight(rs.getFloat("price_per_night"))
                        .setCapacity(rs.getInt("capacity"))
                        .setRoomStatus(RoomStatus.fromString(rs.getString("room_status")))
                        .build();
            }
            return room;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
    }
}

