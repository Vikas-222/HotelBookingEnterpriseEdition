package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.BookingMapper;
import com.example.dao.IBookingDAO;
import com.example.dao.IRoomDAO;
import com.example.dto.BookingDTO;
import com.example.model.Booking;

import java.util.List;

public class BookingService {

    IBookingDAO iBookingDAO;
    IRoomDAO iRoomDAO;

    public BookingService(IBookingDAO iBookingDAO,IRoomDAO iRoomDAO) {
        this.iBookingDAO = iBookingDAO;
        this.iRoomDAO = iRoomDAO;
    }

    public boolean isValidBookingId(int id) throws ApplicationException {
        if (!iBookingDAO.isValidBookingId(id)) {
            throw new ApplicationException(Messages.BookingError.BOOKING_NOT_FOUND);
        }
        return iBookingDAO.isValidBookingId(id);
    }

    public void addBooking(BookingDTO bookingdto) throws DBException {
        Booking booking = BookingMapper.convertBookingDTOToEntity(bookingdto);
        iRoomDAO.isCapacityValid(booking.getRoomNumber(),booking.getNumberOfGuests());
        iBookingDAO.addBooking(booking);
    }

    public BookingDTO getBookingDetails(int id) throws ApplicationException {
        if (!iBookingDAO.isValidBookingId(id)) {
            throw new ApplicationException(Messages.BookingError.BOOKING_NOT_FOUND);
        }
        return BookingMapper.convertEntityToBookingDTO(iBookingDAO.getBookingDetails(id));
    }

    public void updateBooking(Booking booking) throws ApplicationException {
        isValidBookingId(booking.getBookingId());
        iBookingDAO.updateBooking(booking);
    }


    public List<BookingDTO> getAllBookingDetails() throws DBException {
        return BookingMapper.convertEntityListToBookingDTOList(iBookingDAO.getAllBookingDetails());
    }

    public void updateBookingStatus(int id,String status) throws ApplicationException {
        isValidBookingId(id);
        iBookingDAO.updateBookingStatus(id,status);
    }



}
