package com.example.service;

import com.example.common.Messages;
import com.example.common.enums.BookingStatus;
import com.example.common.enums.RoomStatus;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.common.mapper.BookingMapper;
import com.example.controller.validation.BookingValidator;
import com.example.dao.impl.BookingDAOImpl;
import com.example.dao.IBookingDAO;
import com.example.dto.BookingDTO;
import com.example.dto.RoomDTO;
import com.example.dto.UserDTO;
import com.example.model.Booking;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    private final IBookingDAO iBookingDAO = new BookingDAOImpl();
    private final RoomService roomService = new RoomService();
    private final UserService userService = new UserService();

    public void isValidBookingId(int id) throws ApplicationException {
        if (!iBookingDAO.isValidBookingId(id)) {
            throw new ApplicationException(Messages.BookingError.BOOKING_NOT_FOUND);
        }
    }

    public void addBooking(BookingDTO bookingdto, int userId) throws ApplicationException {
        BookingValidator.validateBooking(bookingdto);
        roomService.isValidRoomId(bookingdto.getRoomId());
        RoomDTO room = roomService.getRoomDetails(bookingdto.getRoomId());
        roomService.isCapacityValid(room.getRoomId(), bookingdto.getNumberOfGuests());
        roomService.updateRoomStatus(room.getRoomId(), RoomStatus.OCCUPIED);
        long days = Duration.between(bookingdto.getCheckInTime(), bookingdto.getCheckOutTime()).toDays();
        float gstRate = roomService.getGstRatesByRoomPrice(room.getPricePerNight());
        float gstAmount = calculateGSTByRoomPrice(room.getPricePerNight(), gstRate);
        float serviceCharge = room.getRoomServiceCharge();
        float TotalAmount = calculateTotalAmount(room.getPricePerNight(), gstAmount,serviceCharge, days);
        BookingDTO booking1 = setTotalAmountToBookingId(bookingdto, userId, TotalAmount, gstRate);
        Booking booking = BookingMapper.convertBookingDTOToEntity(booking1);
        iBookingDAO.addBooking(booking, room);
    }

    public BookingDTO setTotalAmountToBookingId(BookingDTO bookingDTO, int userId, float amount, float gstRate) {
        return new BookingDTO.Builder()
                .setBookingId(bookingDTO.getBookingId())
                .setUserId(userId)
                .setRoomId(bookingDTO.getRoomId())
                .setCheckInTime(bookingDTO.getCheckInTime())
                .setCheckOutTime(bookingDTO.getCheckOutTime())
                .setNumberOfGuests(bookingDTO.getNumberOfGuests())
                .setTotalAmount(amount)
                .setRefundAmount(0)
                .setGstRates(gstRate).build();
    }



    public BookingDTO getBookingDetails(String id) throws ApplicationException {
        if (id == null || id.isBlank()) {
            throw new ApplicationException(Messages.BookingError.INVALID_BOOKING_ID);
        }
        int bookingId = Integer.parseInt(id);
        if (bookingId <= 0) {
            throw new ApplicationException(Messages.BookingError.INVALID_BOOKING_ID);
        }
        isValidBookingId(bookingId);
        return BookingMapper.convertEntityToBookingDTO(iBookingDAO.getBookingDetails(bookingId));
    }

    public BookingDTO getBookingDetails(int bookingId) throws ApplicationException {
        if (bookingId <= 0) {
            throw new ApplicationException(Messages.BookingError.INVALID_BOOKING_ID);
        }
        isValidBookingId(bookingId);
        return BookingMapper.convertEntityToBookingDTO(iBookingDAO.getBookingDetails(bookingId));
    }

    public void modifyBooking(BookingDTO bookingDTO, int userId) throws ApplicationException {
        isValidBookingId(bookingDTO.getBookingId());
        Booking booking = iBookingDAO.getBookingDetails(bookingDTO.getBookingId());
        if(booking.getBookingStatus().equals(BookingStatus.CANCELLATION)){
            throw new ApplicationException(Messages.BookingError.CANNOT_MODIFY_CANCELLED_BOOKING);
        }
        RoomDTO room = roomService.getRoomDetails(bookingDTO.getRoomId());
        roomService.updateRoomStatus(room.getRoomId(),RoomStatus.OCCUPIED);
        long days = Duration.between(bookingDTO.getCheckInTime(), bookingDTO.getCheckOutTime()).toDays();
        float gstRate = roomService.getGstRatesByRoomPrice(room.getPricePerNight());
        float gstAmount = calculateGSTByRoomPrice(room.getPricePerNight(), gstRate);
        float serviceCharge = room.getRoomServiceCharge();
        float TotalAmount = calculateTotalAmount(room.getPricePerNight(), gstAmount,serviceCharge, days);
        BookingDTO booking1 = setTotalAmountToBookingId(bookingDTO, userId, TotalAmount, gstRate);
        Booking bookingObj = BookingMapper.convertBookingDTOToEntity(booking1);
        iBookingDAO.modifyBooking(bookingObj, room);
    }


    public List<BookingDTO> getAllBookingDetails() throws DBException {
        return BookingMapper.convertEntityListToBookingDTOList(iBookingDAO.getAllBookingDetails());
    }

    public void updateBookingStatus(int id, String status) throws ApplicationException {
        isValidBookingId(id);
        iBookingDAO.updateBookingStatus(id, status);
    }

    public boolean isValidUserIdAndBookingId(int userId, int bookingId) throws ApplicationException {
        isValidBookingId(bookingId);
        userService.isValidUserId(userId);
        return iBookingDAO.isValidUserIdAndBookingId(userId, bookingId);
    }

    public List<BookingDTO> pastBookings(int userId) throws ApplicationException {
        userService.isValidUserId(userId);
        return BookingMapper.convertEntityListToBookingDTOList(iBookingDAO.getBookingDetailsByUserId(userId));
    }

    public float calculateGSTByRoomPrice(float roomPrice, float gstRate) {
        return (roomPrice * gstRate) / 100;
    }

    public float calculateTotalAmount(float roomPrice, float gstAmount,float serviceCharge, long numberOfDays) {
        return (roomPrice * numberOfDays) + gstAmount + (numberOfDays * serviceCharge);
    }

    public float calculateTotalRefundAmount(float totalAmount, float gstAmount,float serviceCharge, long numberOfDays) {
        if (Math.abs(numberOfDays) >= 2) {
            return totalAmount - (gstAmount + serviceCharge);
        } else if (Math.abs(numberOfDays) == 1) {
            return (totalAmount - (gstAmount + serviceCharge)) - 100;
        } else {
            return 0;
        }
    }

    public float cancelBooking(String id, UserDTO user) throws ApplicationException {
        if (id.isBlank()) {
            throw new ApplicationException(Messages.BookingError.INVALID_BOOKING_ID);
        }
        int bookingId = Integer.parseInt(id);
        if (bookingId <= 0) {
            throw new ApplicationException(Messages.BookingError.INVALID_BOOKING_ID);
        }
        isValidBookingId(bookingId);
        LocalDateTime cancelDate = LocalDateTime.now();
        Booking booking = iBookingDAO.getBookingDetails(bookingId);
        boolean isOwner = (user.getUserId() == booking.getUserId());
        boolean isAdmin = (user.getRole() != null && "Admin".equals(user.getRole()));
        if (!(isOwner || isAdmin)) {
            throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
        }
        RoomDTO room = roomService.getRoomDetails(booking.getRoomId());
        roomService.updateRoomStatus(room.getRoomId(),RoomStatus.AVAILABLE);
        if(cancelDate.isAfter(booking.getCheckOutTime())){
            throw new ApplicationException(Messages.BookingError.CANNOT_CANCEL_PREVIOUS_BOOKING);
        }
        long days = Duration.between(booking.getCheckInTime(), cancelDate).toDays();
        float gstAmount = calculateGSTByRoomPrice(room.getPricePerNight(), booking.getGstRates());
        float serviceCharge = room.getRoomServiceCharge();
        float refundAmount = calculateTotalRefundAmount(booking.getTotalAmount(), gstAmount,serviceCharge, days);
        Date todayDate = Date.valueOf(cancelDate.toLocalDate());
        iBookingDAO.cancelBooking(bookingId, todayDate, refundAmount);
        return refundAmount;
    }


    public float calculateRevenue() throws DBException {
        return iBookingDAO.calculateRevenue();
    }

}
