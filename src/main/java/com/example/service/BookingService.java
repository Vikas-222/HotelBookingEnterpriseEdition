package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.BookingMapper;
import com.example.controller.validation.BookingValidator;
import com.example.dao.IRoomDAO;
import com.example.dao.IUserDAO;
import com.example.dao.impl.BookingDAOImpl;
import com.example.dao.IBookingDAO;
import com.example.dao.impl.RoomDAOImpl;
import com.example.dao.impl.UserDAOImpl;
import com.example.dto.BookingDTO;
import com.example.dto.RoomDTO;
import com.example.model.Booking;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    private IBookingDAO iBookingDAO = new BookingDAOImpl();
    private RoomService roomService = new RoomService();
    private IUserDAO iUserDAO = new UserDAOImpl();
    private IRoomDAO iRoomDAO = new RoomDAOImpl();

    public boolean isValidBookingId(int id) throws ApplicationException {
        if (iBookingDAO.isValidBookingId(id) == false) {
            throw new ApplicationException(Messages.BookingError.BOOKING_NOT_FOUND);
        }
        return true;
    }

    public void addBooking(BookingDTO bookingdto,int userId) throws ApplicationException {
        BookingValidator.validateBooking(bookingdto);
        roomService.isValidRoomId(bookingdto.getRoomId());
        RoomDTO room = roomService.getRoomDetails(bookingdto.getRoomId());
        roomService.isCapacityValid(room.getRoomId(), bookingdto.getNumberOfGuests());
        long days = Duration.between(bookingdto.getCheckInTime(), bookingdto.getCheckOutTime()).toDays();
        float gstRate = iRoomDAO.getGstRatesByRoomPrice(room.getPricePerNight());
        float gstAmount = calculateGSTByRoomPrice(room.getPricePerNight(),gstRate);
        float TotalAmount = calculateTotalAmount(room.getPricePerNight(),gstAmount,days);
        BookingDTO booking1 = setTotalAmountToBookingId(bookingdto,userId,TotalAmount,gstRate);
        Booking booking = BookingMapper.convertBookingDTOToEntity(booking1);
        iBookingDAO.addBooking(booking,room);
    }

    public BookingDTO setTotalAmountToBookingId(BookingDTO bookingDTO,int userId,float amount,float gstRate){
        return new BookingDTO.Builder()
                .setBookingId(bookingDTO.getBookingId())
                .setUserId(userId)
                .setRoomId(bookingDTO.getRoomId())
                .setCheckInTime(bookingDTO.getCheckInTime())
                .setCheckOutTime(bookingDTO.getCheckOutTime())
                .setNumberOfGuests(bookingDTO.getNumberOfGuests())
                .setTotalAmount(amount)
                .setGstRates(gstRate).build();
    }

    public BookingDTO getBookingDetails(int id) throws ApplicationException {
        iBookingDAO.isValidBookingId(id);
        return BookingMapper.convertEntityToBookingDTO(iBookingDAO.getBookingDetails(id));
    }

    public void modifyBooking(BookingDTO bookingDTO,int userId) throws ApplicationException {
        isValidBookingId(bookingDTO.getBookingId());
        RoomDTO room = roomService.getRoomDetails(bookingDTO.getRoomId());
        long days = Duration.between(bookingDTO.getCheckInTime(), bookingDTO.getCheckOutTime()).toDays();
        float gstRate = iRoomDAO.getGstRatesByRoomPrice(room.getPricePerNight());
        float gstAmount = calculateGSTByRoomPrice(room.getPricePerNight(),gstRate);
        float TotalAmount = calculateTotalAmount(room.getPricePerNight(),gstAmount,days);
        BookingDTO booking1 = setTotalAmountToBookingId(bookingDTO,userId,TotalAmount,gstRate);
        Booking booking = BookingMapper.convertBookingDTOToEntity(booking1);
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

    public void getRoomPrice(int id, String status) throws ApplicationException {
        isValidBookingId(id);
        iBookingDAO.updateBookingStatus(id, status);
    }

    public float calculateGSTByRoomPrice(float roomPrice,float gstRate) {
        return (roomPrice * gstRate) / 100;
    }

    public float calculateTotalAmount(float roomPrice,float gstAmount,long numberOfDays) {
        return (roomPrice * numberOfDays) + gstAmount;
    }

//    public cancelBooking(BookingDTO bookingDTO){
//        iBookingDAO.cancelBooking(bookingDTO.getBookingId(), Timestamp.valueOf(bookingDTO.getCancellationDate()),bookingDTO.getRefundAmount())
//    }

}
