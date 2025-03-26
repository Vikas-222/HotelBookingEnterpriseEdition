package com.example.dao;

import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.model.RoomImages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomImagesDAOImpl implements IRoomImagesDAO {

    @Override
    public void saveRoomImage(RoomImages roomImage) throws DBException {
        String sql = "INSERT INTO room_images (imagepath, room_id) VALUES (?, ?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, roomImage.getImagepath());
            pst.setInt(2, roomImage.getRoomId());
//            pst.setTimestamp(3, Timestamp.valueOf(roomImage.getCreatedAt()));
//            pst.setTimestamp(4, Timestamp.valueOf(roomImage.getUpdatedAt()));
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<RoomImages> getRoomImagesByRoomId(int roomId) throws DBException {
        List<RoomImages> imageList = new ArrayList<>();
        String sql = "SELECT * FROM room_images WHERE room_id = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            rs = stmt.executeQuery();
            while (rs.next()) {

                RoomImages image = new RoomImages();

                image.setId(rs.getInt("id"));
                image.setImagepath(rs.getString("imagepath"));
                image.setRoomId(rs.getInt("room_id"));
                image.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                image.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                imageList.add(image);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
        return imageList;

    }
}
