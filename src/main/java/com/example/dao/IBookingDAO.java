package com.example.dao;

import com.example.common.exception.DBException;
import com.example.model.Booking;

import java.util.List;

public interface IBookingDAO {

    void addBooking(Booking booking) throws DBException;

    Booking getBookingDetails(int id) throws DBException;

    void updateBooking(Booking booking) throws DBException;

    void updateBookingStatus(int id, String status) throws DBException;

    List<Booking> getAllBookingDetails() throws DBException;

    boolean isValidBookingId(int bookingId) throws DBException;
}
