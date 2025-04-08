package com.example.dao;

import com.example.common.exception.DBException;
import com.example.dto.RoomDTO;
import com.example.model.Booking;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface IBookingDAO {

    void addBooking(Booking booking, RoomDTO room) throws DBException;

    Booking getBookingDetails(int id) throws DBException;

    void updateBookingStatus(int id, String status) throws DBException;

    List<Booking> getAllBookingDetails() throws DBException;

    boolean isValidBookingId(int bookingId) throws DBException;

    boolean cancelBooking(int bookingId, Date cancellationDate, float refundAmount) throws DBException;

    boolean modifyBooking(Booking booking, RoomDTO room) throws DBException;

    boolean isValidUserIdAndBookingId(int userId, int bookingId) throws DBException;

    List<Booking> getBookingDetailsByUserId(int userId) throws DBException;

    float calculateRevenue() throws DBException;
}
