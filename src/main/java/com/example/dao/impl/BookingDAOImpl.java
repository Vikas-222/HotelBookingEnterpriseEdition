package com.example.dao.impl;

import com.example.common.enums.BookingStatus;
import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dao.IBookingDAO;
import com.example.dto.RoomDTO;
import com.example.model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements IBookingDAO {

    @Override
    public void addBooking(Booking booking, RoomDTO room) throws DBException {
        String insert = "insert into booking (user_id,room_id,check_in,check_out,total_amount,numberOfGuests,refund_amount,gstRate) values (?,?,?,?,?,?,?,?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(insert)) {
            pst.setInt(1, booking.getUserId());
            pst.setInt(2, booking.getRoomId());
            pst.setTimestamp(3, Timestamp.valueOf(booking.getCheckInTime()));
            pst.setTimestamp(4, Timestamp.valueOf(booking.getCheckOutTime()));
            pst.setFloat(5, booking.getTotalAmount());
            pst.setInt(6, booking.getNumberOfGuests());
            pst.setFloat(7, booking.getRefundAmount());
            pst.setFloat(8, booking.getGstRates());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
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
            while (rs.next()) {
                Date sqlDate = rs.getDate("cancellation_date");
                LocalDate cancellationDate = null;
                if (sqlDate != null) {
                    cancellationDate = sqlDate.toLocalDate();
                }
                booking = new Booking.Builder()
                        .setBookingId(rs.getInt("booking_id"))
                        .setUserId(rs.getInt("user_id"))
                        .setRoomId(rs.getInt("room_id"))
                        .setCheckInTime(rs.getTimestamp("check_in").toLocalDateTime())
                        .setCheckOutTime(rs.getTimestamp("check_out").toLocalDateTime())
                        .setTotalAmount(rs.getFloat("total_amount"))
                        .setGstRates(rs.getFloat("gstRate"))
                        .setBookingStatus(BookingStatus.fromString(rs.getString("booking_status")))
                        .setCancellationDate(cancellationDate)
                        .setRefundAmount(rs.getFloat("refund_amount")).build();
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
        return booking;
    }


    @Override
    public void updateBookingStatus(int id, String status) throws DBException {
        String update = "update Booking set booking_status = ? where booking_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(update)) {
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
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(fetch)) {
            rs = pst.executeQuery();
            while (rs.next()) {
                Date sqlDate = rs.getDate("cancellation_date");
                LocalDate cancellationDate = null;
                if (sqlDate != null) {
                    cancellationDate = sqlDate.toLocalDate();
                }
                Booking booking = new Booking.Builder()
                        .setBookingId(rs.getInt("booking_id"))
                        .setUserId(rs.getInt("user_id"))
                        .setRoomId(rs.getInt("room_id"))
                        .setCheckInTime(rs.getTimestamp("check_in").toLocalDateTime())
                        .setCheckOutTime(rs.getTimestamp("check_out").toLocalDateTime())
                        .setTotalAmount(rs.getFloat("total_amount"))
                        .setGstRates(rs.getFloat("gstRate"))
                        .setBookingStatus(BookingStatus.fromString(rs.getString("booking_status")))
                        .setCancellationDate(cancellationDate)
                        .setRefundAmount(rs.getFloat("refund_amount"))
                        .setNumberOfGuests(rs.getInt("numberOfGuests"))
                        .build();
                list.add(booking);
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
        return list;
    }


    @Override
    public boolean isValidBookingId(int bookingId) throws DBException {
        String find = "select * from booking where booking_id = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(find)) {
            preparedStatement.setInt(1, bookingId);
            rs = preparedStatement.executeQuery();
            return rs.next();
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
    public boolean cancelBooking(int bookingId, Date cancellationDate, float refundAmount) throws DBException {
        String sql = "UPDATE booking SET booking_status = 'CANCELLATION', cancellation_date = ?, refund_amount = ? WHERE booking_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setDate(1, cancellationDate);
            pst.setFloat(2, refundAmount);
            pst.setInt(3, bookingId);
            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean modifyBooking(Booking booking, RoomDTO room) throws DBException {
        String sql = "UPDATE booking SET check_in = ?, check_out = ?, numberOfGuests = ?,total_amount = ?,refund_amount = 0, updated_at = CURRENT_TIMESTAMP WHERE booking_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            pst.setTimestamp(1, Timestamp.valueOf(booking.getCheckInTime()));
            pst.setTimestamp(2, Timestamp.valueOf(booking.getCheckOutTime()));
            pst.setInt(3, booking.getNumberOfGuests());
            pst.setFloat(4, booking.getTotalAmount());
            pst.setInt(5, booking.getBookingId());
            if (pst.executeUpdate() > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean isValidUserIdAndBookingId(int userId, int bookingId) throws DBException {
        String sql = "select * from booking where user_id = ? and booking_id = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, userId);
            pst.setInt(2, bookingId);
            rs = pst.executeQuery();
            return rs.next();
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
    public List<Booking> getBookingDetailsByUserId(int userId) throws DBException {
        String fetch = "select * from booking where user_id = ?";
        ResultSet rs = null;
        List<Booking> list = new ArrayList<>();
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(fetch)) {
            pst.setInt(1, userId);
            rs = pst.executeQuery();
            while (rs.next()) {
                Date sqlDate = rs.getDate("cancellation_date");
                LocalDate cancellationDate = null;
                if (sqlDate != null) {
                    cancellationDate = sqlDate.toLocalDate();
                }
                Booking booking = new Booking.Builder()
                        .setBookingId(rs.getInt("booking_id"))
                        .setUserId(rs.getInt("user_id"))
                        .setRoomId(rs.getInt("room_id"))
                        .setCheckInTime(rs.getTimestamp("check_in").toLocalDateTime())
                        .setCheckOutTime(rs.getTimestamp("check_out").toLocalDateTime())
                        .setTotalAmount(rs.getFloat("total_amount"))
                        .setGstRates(rs.getFloat("gstRate"))
                        .setBookingStatus(BookingStatus.fromString(rs.getString("booking_status")))
                        .setCancellationDate(cancellationDate)
                        .setRefundAmount(rs.getFloat("refund_amount"))
                        .setNumberOfGuests(rs.getInt("numberOfGuests")).build();
                list.add(booking);
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
        return list;
    }

    @Override
    public float calculateRevenue() throws DBException {
        String sql = "SELECT (SELECT COALESCE(SUM(total_amount), 0) FROM booking WHERE booking_status ='CONFIRMED') + " +
                "(SELECT (SELECT COALESCE(SUM(total_amount), 0) FROM booking WHERE booking_status ='COMPLETED')) -" +
                "(SELECT COALESCE(SUM(refund_amount), 0) FROM booking WHERE booking_status = 'CANCELLATION') AS net_revenue";
        ResultSet rs = null;
        try (Connection conn = DbConnect.instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getFloat("net_revenue");
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


}
