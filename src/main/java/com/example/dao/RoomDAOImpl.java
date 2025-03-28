package com.example.dao;

import com.example.common.enums.RoomType;
import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.sql.*;
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
    public void updateRoomStatus(int roomNumber, boolean status) throws DBException {
        String updateStatus = "update room set is_active = ? where room_number = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(updateStatus)) {
            pstUpdate.setBoolean(1, status);
            pstUpdate.setInt(2, roomNumber);
            pstUpdate.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

//    @Override
//    public List<Room> getAllRooms() throws DBException {
//        String fetch = "select * from room";
//        ResultSet rs = null;
//        List<Room> roomList = new ArrayList<>();
//        try (Connection connection = DbConnect.instance.getConnection();
//             PreparedStatement pstFind = connection.prepareStatement(fetch)) {
//            rs = pstFind.executeQuery();
//            while (rs.next()) {
//                String roomType = rs.getString("room_type");
//                Room room = new Room.Builder().setRoomId(rs.getInt("room_id")).setRoomNumber(rs.getInt("room_number"))
//                        .setRoomType(RoomType.fromString(roomType)).setCapacity(rs.getInt("capacity"))
//                        .setPricePerNight(rs.getFloat("price_per_night")).setActive(rs.getBoolean("is_active")).build();
//                roomList.add(room);
//            }
//            return roomList;
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new DBException(Messages.Error.FAILED);
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    throw new DBException(e);
//                }
//            }
//        }
//    }

    @Override
    public boolean isCapacityValid(int roomNumber, int numberOfGuests) throws DBException {
        String sql = "SELECT capacity FROM room WHERE room_number = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setInt(1, roomNumber);
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
        String sql = "select r.room_id,r.room_number,r.room_type,r.capacity,r.price_per_night,r.is_active,im.imagepath from room r, room_images im where r.room_id = im.room_id";
        ResultSet rs = null;
        RoomDTO roomDTO = null;
        List<RoomDTO> roomList = new ArrayList<>();
        try (Connection connection = DbConnect.instance.getConnection();
             Statement pst = connection.createStatement()) {
             rs = pst.executeQuery(sql);
             while(rs.next()){
                 roomDTO = new RoomDTO.Builder()
                         .setRoomId(rs.getInt("room_id"))
                         .setRoomNumber(rs.getInt("room_number"))
                         .setRoomType(RoomType.fromString(rs.getString("room_type")))
                         .setCapacity(rs.getInt("capacity"))
                         .setPricePerNight(rs.getFloat("price_per_night"))
                         .setIsActive(rs.getBoolean("is_active"))
                         .setImagePath(rs.getString("imagepath")).build();
                 roomList.add(roomDTO);
             }
        } catch (SQLException | ClassNotFoundException e) {
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
        return roomList;
    }

}

