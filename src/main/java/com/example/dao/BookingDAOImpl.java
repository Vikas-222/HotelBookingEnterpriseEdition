package com.example.dao;

import com.example.common.enums.BookingStatus;
import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements IBookingDAO {

    @Override
    public void addBooking(Booking booking) throws DBException {
        String insert = "insert into booking (user_id,room_number,check_in,check_out,total_amount,numberOfGuests) values (?,?,?,?,?,?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(insert);) {
            pst.setInt(1, booking.getUserId());
            pst.setInt(2, booking.getRoomNumber());
            pst.setTimestamp(3, Timestamp.valueOf(booking.getCheckInTime()));
            pst.setTimestamp(4, Timestamp.valueOf(booking.getCheckOutTime()));
            pst.setFloat(5, booking.getTotalAmount());
            pst.setInt(6, booking.getNumberOfGuests());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("dao booking "+booking);
            System.out.println("dao " + e.getMessage());
            throw new DBException(e);
        }
    }

    @Override
    public Booking getBookingDetails(int id) throws DBException {
        String fetch = "select * from booking where booking_id = ?";
        ResultSet rs = null;
        Booking booking = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(fetch)) {
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                booking = new Booking.Builder()
                        .setBookingId(rs.getInt("booking_id"))
                        .setUserId(rs.getInt("user_id"))
                        .setRoomNumber(rs.getInt("room_number"))
                        .setCheckInTime(rs.getTimestamp("check_in").toLocalDateTime())
                        .setCheckOutTime(rs.getTimestamp("check_out").toLocalDateTime())
                        .setTotalAmount(rs.getFloat("total_amount"))
                        .setBookingStatus(BookingStatus.fromString(rs.getString("booking_status")))
                        .setCancellationDate(rs.getDate("cancellation_date"))
                        .setRefundAmount(rs.getFloat("refund_amount")).build();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("dao " + e.getMessage());
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
        return booking;
    }

    @Override
    public void updateBooking(Booking booking) throws DBException {
        String update = "update booking set room_number = ?, check_in = ?, check_out = ?, booking_status = ? where booking_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(update);) {
                pst.setInt(1, booking.getRoomNumber());
                pst.setTimestamp(2, Timestamp.valueOf(booking.getCheckInTime()));
                pst.setTimestamp(3, Timestamp.valueOf(booking.getCheckOutTime()));
                pst.setString(4, booking.getBookingStatus().toString());
                pst.setInt(5, booking.getBookingId());
                pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("update dao " + e.getMessage());
            throw new DBException(e);
        }
    }

    @Override
    public void updateBookingStatus(int id, String status) throws DBException {
        String update = "update Booking set booking_status = ? where booking_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(update);) {
            pst.setString(1, status);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Booking> getAllBookingDetails() throws DBException {
        String fetch = "select * from booking";
        ResultSet rs = null;
        List<Booking> list = new ArrayList<>();
        Booking booking;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(fetch);) {
            rs = pst.executeQuery();
            if (rs.next()) {
                booking = new Booking.Builder().setBookingId(rs.getInt("booking_id"))
                        .setUserId(rs.getInt("user_id"))
                        .setRoomNumber(rs.getInt("room_number"))
                        .setCheckInTime(rs.getTimestamp("check_in").toLocalDateTime())
                        .setCheckOutTime(rs.getTimestamp("check_out").toLocalDateTime())
                        .setTotalAmount(rs.getFloat("total_amount"))
                        .setBookingStatus(BookingStatus.fromString(rs.getString("booking_status")))
                        .setCancellationDate(rs.getDate("cancellation_date"))
                        .setRefundAmount(rs.getFloat("refund_amount"))
                        .build();
                list.add(booking);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("dao " + e.getMessage());
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
        return list;
    }


    @Override
    public boolean isValidBookingId(int bookingId) throws DBException {
        String find = "select * from booking where booking_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(find)) {
            preparedStatement.setInt(1, bookingId);
            ResultSet rs = preparedStatement.executeQuery();
           return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

}
