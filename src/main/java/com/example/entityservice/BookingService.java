package com.example.entityservice;

import com.example.common.Messages;
import com.example.common.entitymapper.BookingMapper;
import com.example.common.enums.RoomStatus;
import com.example.common.exception.ApplicationException;
import com.example.controller.validation.BookingValidator;
import com.example.dao.entity.BookingDAO;
import com.example.dto.BookingDTO;
import com.example.dto.RoomDTO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.Booking;
import com.example.service.RoomService;

import java.time.Duration;

public class BookingService {

    private BookingDAO bookingDAO = new BookingDAO();
    private RoomService roomService = new RoomService();
    private UserServices userService = new UserServices();

    public boolean isValidBookingId(int id) throws ApplicationException {
        if (bookingDAO.isValidBookingId(id) == false) {
            throw new ApplicationException(Messages.BookingError.BOOKING_NOT_FOUND);
        }
        return true;
    }

    public Booking addBooking(BookingDTO bookingdto, UsersDTO user) throws ApplicationException {
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
        BookingDTO booking1 = setTotalAmountToBookingId(bookingdto, user.getUserId(), TotalAmount, gstRate);
        Booking booking = BookingMapper.convertBookingDTOToEntity(booking1,user,room);
        return bookingDAO.addBooking(booking, room);
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

    public boolean isValidUserIdAndBookingId(int userId, int bookingId) throws ApplicationException {
        isValidBookingId(bookingId);
        userService.isValidUserId(userId);
        return bookingDAO.isValidUserIdAndBookingId(userId, bookingId);
    }

    public BookingDTO getBookingDetails(int bookingId) throws ApplicationException {
        if (bookingId <= 0) {
            throw new ApplicationException(Messages.BookingError.INVALID_BOOKING_ID);
        }
        isValidBookingId(bookingId);
        return BookingMapper.convertEntityToBookingDTO(bookingDAO.getBookingDetails(bookingId));
    }

}
