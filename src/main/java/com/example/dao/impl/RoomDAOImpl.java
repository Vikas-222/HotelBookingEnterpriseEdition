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
    public Room addRoom(RoomDTO roomDTO) throws DBException {
        String insertRoomSql = "INSERT INTO room(room_number, room_type, capacity, price_per_night) VALUES (?, ?, ?, ?)";
        String insertRoomImageSql = "INSERT INTO room_images(imagepath, room_id) VALUES (?, ?)";

        Connection connection = null;
        PreparedStatement pstRoom = null;
        PreparedStatement pstImage = null;
        ResultSet generatedKeys = null;
        Room newRoom;

        try {
            connection = DbConnect.instance.getConnection();
            connection.setAutoCommit(false);

            pstRoom = connection.prepareStatement(insertRoomSql, Statement.RETURN_GENERATED_KEYS);
            pstRoom.setInt(1, roomDTO.getRoomNumber());
            pstRoom.setString(2, roomDTO.getRoomType().toString());
            pstRoom.setInt(3, roomDTO.getCapacity());
            pstRoom.setFloat(4, roomDTO.getPricePerNight());

            int affectedRows = pstRoom.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating room failed, no rows affected.");
            }

            //retrieve the roomId
            int roomId = -1;
            generatedKeys = pstRoom.getGeneratedKeys();
            if (generatedKeys.next()) {
                roomId = generatedKeys.getInt(1);

                newRoom = new Room.Builder().setRoomId(roomId)
                        .setRoomNumber(roomDTO.getRoomNumber())
                        .setRoomType(roomDTO.getRoomType()).setCapacity(roomDTO.getCapacity())
                        .setPricePerNight(roomDTO.getPricePerNight()).build();
                new ArrayList<>(roomDTO.getImagePath());
            } else {
                throw new SQLException("Creating room failed, no ID obtained.");
            }

            pstImage = connection.prepareStatement(insertRoomImageSql);

            if (roomDTO.getImagePath() != null && !roomDTO.getImagePath().isEmpty()) {
                for (String imagePath : roomDTO.getImagePath()) {
                    pstImage.setString(1, imagePath);
                    pstImage.setInt(2, roomId);
                    pstImage.addBatch();
                }

                int[] result = pstImage.executeBatch();
                boolean allImagesInsertedSuccessfully = Arrays.stream(result).allMatch(i -> i > 0);

                if (!allImagesInsertedSuccessfully) {
                    throw new SQLException("Not all images were inserted successfully.");
                }
            }
            connection.commit();
            return newRoom;

        } catch (SQLException | ClassNotFoundException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DBException(ex);
                }
            }
            throw new DBException(e);
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (pstRoom != null) pstRoom.close();
                if (pstImage != null) pstImage.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new DBException(ex);
            }
        }
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
    public void updateRoomStatus(int roomId, RoomStatus status) throws DBException {
        String updateStatus = "update room set room_status = ? where room_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pstUpdate = connection.prepareStatement(updateStatus)) {
            pstUpdate.setString(1, status.toString());
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
             PreparedStatement pst = connection.prepareStatement(sql)) {
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
        ResultSet rs;
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
        String sql = "SELECT r.*, rsc.charge_per_night,ri.imagepath FROM room r JOIN room_service_charge rsc on r.room_type = rsc.room_type " +
                "JOIN room_images ri on r.room_id = ri.room_id where r.room_status = 'AVAILABLE'";
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
                            .setRoomServiceCharge(rs.getFloat("charge_per_night"))
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
    public float getGstRatesByRoomPrice(float price) throws DBException {
        String sql = "SELECT tax_rate FROM gst_rates WHERE min_price <= ?AND(max_price >= ? OR max_price IS NULL)";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setFloat(1, price);
            pst.setFloat(2, price);
            rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getFloat("tax_rate");
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


    @Override
    public RoomDTO getRoomById(int roomId) throws DBException {
        String sql = "SELECT r.*, rsc.charge_per_night,ri.imagepath FROM room r JOIN room_service_charge rsc JOIN room_images ri " +
                "on r.room_id = ri.room_id and r.room_type = rsc.room_type WHERE r.room_id = ?";
        RoomDTO.Builder room = null;
        List<String> imageList = new ArrayList<>();
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
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
                            .setRoomStatus(RoomStatus.fromString(rs.getString("room_status")))
                            .setRoomServiceCharge(rs.getFloat("charge_per_night"));
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
    public Float getRoomServiceCharge(RoomType roomType) throws DBException {
        String sql = "select charge_per_night from room_service_charge where room_type = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, roomType.toString());
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }catch(Exception e){
            throw new DBException(e);
        }
        return 0.0f;
    }
}

