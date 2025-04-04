package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.BookingMapper;
import com.example.controller.validation.BookingValidator;
import com.example.dao.IUserDAO;
import com.example.dao.impl.BookingDAOImpl;
import com.example.dao.IBookingDAO;
import com.example.dao.impl.UserDAOImpl;
import com.example.dto.BookingDTO;
import com.example.dto.RoomDTO;
import com.example.model.Booking;
import java.util.List;

public class BookingService {

    private IBookingDAO iBookingDAO = new BookingDAOImpl();
    private RoomService roomService = new RoomService();
    private IUserDAO iUserDAO = new UserDAOImpl();

    public boolean isValidBookingId(int id) throws ApplicationException {
        if (iBookingDAO.isValidBookingId(id) == false) {
            throw new ApplicationException(Messages.BookingError.BOOKING_NOT_FOUND);
        }
        return true;
    }

    public void addBooking(BookingDTO bookingdto) throws ApplicationException {
        BookingValidator.validateBooking(bookingdto);
        roomService.isValidRoomId(bookingdto.getRoomId());
        RoomDTO room = roomService.getRoomDetails(bookingdto.getRoomId());
        Booking booking = BookingMapper.convertBookingDTOToEntity(bookingdto);
        roomService.isCapacityValid(room.getRoomId(), booking.getNumberOfGuests());
        iBookingDAO.addBooking(booking,room);
    }

    public BookingDTO getBookingDetails(int id) throws ApplicationException {
        iBookingDAO.isValidBookingId(id);
        return BookingMapper.convertEntityToBookingDTO(iBookingDAO.getBookingDetails(id));
    }

    public void modifyBooking(BookingDTO bookingDTO) throws ApplicationException {
        isValidBookingId(bookingDTO.getBookingId());
        RoomDTO room = roomService.getRoomDetails(bookingDTO.getRoomId());
        Booking booking = BookingMapper.convertBookingDTOToEntity(bookingDTO);
        iBookingDAO.modifyBooking(booking,room);
    }


    public List<BookingDTO> getAllBookingDetails() throws DBException {
        return BookingMapper.convertEntityListToBookingDTOList(iBookingDAO.getAllBookingDetails());
    }

    public void updateBookingStatus(int id, String status) throws ApplicationException {
        isValidBookingId(id);
        iBookingDAO.updateBookingStatus(id, status);
    }

    public boolean isValidUserIdAndBookingId(int userId,int bookingId) throws ApplicationException {
        isValidBookingId(bookingId);
        iUserDAO.isValidUserId(userId);
        return iBookingDAO.isValidUserIdAndBookingId(userId, bookingId);
    }

    public List<BookingDTO> pastBookings(int userId) throws DBException {
        iUserDAO.isValidUserId(userId);
        return BookingMapper.convertEntityListToBookingDTOList(iBookingDAO.getBookingDetailsByUserId(userId));
    }

}
