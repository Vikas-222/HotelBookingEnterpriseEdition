//package com.example.dao;
//
//import com.example.common.Messages;
//import com.example.db.DbConnect;
//import com.example.entity.Room;
//import com.example.exception.ApplicationException;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class RoomDao {
//    Connection connection;
//    ResultSet rs;
//
//    public int addRoom(Room room) throws ApplicationException {
//        connection = DbConnect.getConnection();
//        String findRoom = "select * from room where room_number = ?";
//
//        PreparedStatement preparedStatement = connection.prepareStatement(findRoom);
//        preparedStatement.setInt(1, room.getRoomNumber());
//        rs = preparedStatement.executeQuery();
//
//        if (rs.next()) {
//            throw new AlreadyExistsException(Messages.Error.ROOMEXISTS);
//        } else {
//
//            String insert = "insert into room(room_number,room_type,capacity,price_per_night,images) values (?,?,?,?,?)";
//
//            PreparedStatement pst = connection.prepareStatement(insert);
//            pst.setInt(1, room.getRoomNumber());
//            pst.setString(2, room.getRoomType().toString());
//            pst.setInt(3, room.getCapacity());
//            pst.setFloat(4, room.getPricePerNight());
//            pst.setString(5, room.getImagePath());
//            int row = pst.executeUpdate();
//            return row;
//        }
//    }
//
//
//}
