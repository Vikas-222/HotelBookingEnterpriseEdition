package com.example.entityservice;

import com.example.common.Messages;
import com.example.common.entitymapper.BookingMapper;
import com.example.common.enums.BookingStatus;
import com.example.common.enums.Role;
import com.example.common.enums.RoomStatus;
import com.example.common.exception.ApplicationException;
import com.example.controller.validation.BookingValidator;
import com.example.dao.entity.BookingDAO;
import com.example.dto.BookingDTO;
import com.example.dto.RoomDTO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.Booking;
import com.example.service.RoomService;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;

public class BookingService {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final RoomService roomService = new RoomService();
    private final UserServices userService = new UserServices();

    public void isValidBookingId(int id) throws ApplicationException {
        if (!bookingDAO.isValidBookingId(id)) {
            throw new ApplicationException(Messages.BookingError.BOOKING_NOT_FOUND);
        }
    }

    public Booking addBooking(BookingDTO bookingdto, UsersDTO usersDTO) throws ApplicationException {
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

        Booking booking = new Booking.Builder()
                .setCheckInTime(bookingdto.getCheckInTime())
                .setCheckOutTime(bookingdto.getCheckOutTime())
                .setTotalAmount(TotalAmount)
                .setBookingStatus(BookingStatus.CONFIRMED) // Or whatever default status you have
                .setGstRates(gstRate)
                .setNumberOfGuests(bookingdto.getNumberOfGuests())
                .build();

        // Pass IDs to DAO
        return bookingDAO.addBooking(booking, usersDTO.getUserId(), bookingdto.getRoomId());

//        BookingDTO booking1 = setTotalAmountToBookingId(bookingdto, usersDTO.getUserId(), TotalAmount, gstRate);
//        Booking booking = BookingMapper.convertBookingDTOToEntity(booking1,usersDTO,room);
//        return bookingDAO.addBooking(booking);
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


    public float cancelBooking(String id, UsersDTO user) throws ApplicationException {
        if (id.isBlank()) {
            throw new ApplicationException(Messages.BookingError.INVALID_BOOKING_ID);
        }
        int bookingId = Integer.parseInt(id);
        if (bookingId <= 0) {
            throw new ApplicationException(Messages.BookingError.INVALID_BOOKING_ID);
        }
        isValidBookingId(bookingId);
        LocalDateTime cancelDate = LocalDateTime.now();
        Booking booking = bookingDAO.getBookingDetails(bookingId);
        boolean isOwner = (user.getUserId() == booking.getUser().getUserId());
        boolean isAdmin = (user.getRole() != null && Role.ADMIN.equals(user.getRole()));
        if (!(isOwner || isAdmin)) {
            throw new ApplicationException(Messages.Error.UNAUTHORIZED_ACCESS);
        }
        RoomDTO room = roomService.getRoomDetails(booking.getRoom().getRoomId());
        roomService.updateRoomStatus(room.getRoomId(),RoomStatus.AVAILABLE);
        if(cancelDate.isAfter(booking.getCheckOutTime())){
            throw new ApplicationException(Messages.BookingError.CANNOT_CANCEL_PREVIOUS_BOOKING);
        }
        long days = Duration.between(booking.getCheckInTime(), cancelDate).toDays();
        float gstAmount = calculateGSTByRoomPrice(room.getPricePerNight(), booking.getGstRates());
        float serviceCharge = room.getRoomServiceCharge();
        float refundAmount = calculateTotalRefundAmount(booking.getTotalAmount(), gstAmount,serviceCharge, days);
        Date todayDate = Date.valueOf(cancelDate.toLocalDate());
        bookingDAO.cancelBooking(bookingId, todayDate, refundAmount);
        return refundAmount;
    }

}
