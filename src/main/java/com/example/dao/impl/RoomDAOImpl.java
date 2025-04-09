package com.example.dao.impl;

import com.example.common.enums.RoomStatus;
import com.example.common.enums.RoomType;
import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dao.IRoomDAO;
import com.example.dto.RoomDTO;
import com.example.model.Room;

import java.sql.*;
import java.util.*;

public class RoomDAOImpl implements IRoomDAO {

    @Override
    public boolean addRoom(RoomDTO room) throws DBException {
        String insert = "INSERT INTO room(room_number, room_type, capacity, price_per_night) VALUES (?, ?, ?, ?)";
        String roomImages = "INSERT INTO room_images(imagepath, room_id) VALUES (?, ?)";

        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstImage = connection.prepareStatement(roomImages)) {

            pst.setInt(1, room.getRoomNumber());
            pst.setString(2, room.getRoomType().toString());
            pst.setInt(3, room.getCapacity());
            pst.setFloat(4, room.getPricePerNight());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int roomId = rs.getInt(1);
                for (String imagePath : room.getImagePath()) {
                    pstImage.setString(1, imagePath);
                    pstImage.setInt(2, roomId);
                    pstImage.addBatch();
                }
                int[] result = pstImage.executeBatch();
                return Arrays.stream(result).allMatch(i -> i > 0);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
        return false;
    }

    @Override
    public boolean isRoomNumberExists(int roomNumber) throws DBException {
        String findRoom = "select * from room where room_number = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findRoom)) {
            preparedStatement.setInt(1, roomNumber);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
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
    public void updateRoomStatus(int roomId, String status) throws DBException {
        String updateStatus = "update room set room_status = ? where room_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(updateStatus)) {
            pstUpdate.setString(1, status);
            pstUpdate.setInt(2, roomId);
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
            throw new DBException(e);
        }
        return false;
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
        return new ArrayList<>(roomMap.values());
    }

    @Override
    public RoomDTO getRoom(int roomId) throws DBException {
        String sql = "SELECT r.room_number,r.room_type,r.capacity,r.price_per_night,r.room_status,img.imagepath " +
                "FROM room as r, room_images as img where r.room_id = img.room_id and r.room_id = ?";
        ResultSet rs = null;
        RoomDTO.Builder room = null;
        List<String> imageList = new ArrayList<>();
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setInt(1, roomId);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (room == null) {
                    room = new RoomDTO.Builder()
                            .setRoomId(roomId)
                            .setRoomNumber(rs.getInt("room_number"))
                            .setRoomType(RoomType.fromString(rs.getString("room_type")))
                            .setPricePerNight(rs.getFloat("price_per_night"))
                            .setCapacity(rs.getInt("capacity"))
                            .setRoomStatus(RoomStatus.fromString(rs.getString("room_status")));
                }
                imageList.add(rs.getString("imagepath"));
            }
            RoomDTO roomDTO = null;
            if (room != null) {
                roomDTO = room.setImagePath(imageList).build();
            }
            return roomDTO;
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

    @Override
    public float getGstRatesByRoomPrice(float price) throws DBException {
        String sql = "SELECT tax_rate FROM gst_rates WHERE min_price <= ?AND(max_price >= ? OR max_price IS NULL)";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setFloat(1, price);
            pst.setFloat(2, price);
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getFloat("tax_rate");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }finally {
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
    public boolean updateRoom(RoomDTO room) throws DBException {
        String update = "UPDATE room SET room_number = ?, room_type = ?, capacity = ?, price_per_night = ?, room_status = ? WHERE room_id = ?";
        String deleteRoomImages = "DELETE FROM room_images WHERE room_id = ?";
        String insertRoomImages = "INSERT INTO room_images(imagepath, room_id) VALUES (?, ?)";


        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(update);
             PreparedStatement pstDeleteImages = connection.prepareStatement(deleteRoomImages);
             PreparedStatement pstInsertImages = connection.prepareStatement(insertRoomImages)) {
            connection.setAutoCommit(false);
            pstUpdate.setInt(1, room.getRoomNumber());
            pstUpdate.setString(2, room.getRoomType().toString());
            pstUpdate.setInt(3, room.getCapacity());
            pstUpdate.setFloat(4, room.getPricePerNight());
            pstUpdate.setString(5, room.getRoomStatus().toString());
            pstUpdate.setInt(6, room.getRoomId());
            int roomUpdateResult = pstUpdate.executeUpdate();

            if (roomUpdateResult > 0) {
                pstDeleteImages.setInt(1, room.getRoomId());
                pstDeleteImages.executeUpdate();

                if (room.getImagePath() != null && !room.getImagePath().isEmpty()) {
                    for (String imagePath : room.getImagePath()) {
                        pstInsertImages.setString(1, imagePath);
                        pstInsertImages.setInt(2, room.getRoomId());
                        pstInsertImages.addBatch();
                    }
                    int[] imageInsertResult = pstInsertImages.executeBatch();
                    boolean allImagesInserted = Arrays.stream(imageInsertResult).allMatch(i -> i >= 0);
                    if (allImagesInserted) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.commit();
                    return true;
                }
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}

